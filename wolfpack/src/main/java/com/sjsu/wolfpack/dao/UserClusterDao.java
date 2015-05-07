package com.sjsu.wolfpack.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.UserCluster;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


@Repository
public class UserClusterDao
{
	private final JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public UserClusterDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createUserCluster(final UserCluster cluster) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate
					.update(
							"insert into hyg_usercluster (cluster_id, cluster_type, user_id, age, weight, calories) values (?, ?, ?, ?, ?, ?)",
							cluster.getClusterid(), cluster.getClusterType(), cluster.getUser_Id(), cluster.getAge(),
							cluster.getWeight(), cluster.getCalories());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(cluster.getClusterid());
		}
	}

	public UserCluster findUserClusterByIdUser(final String cluster_id, final String user_id)
	{
		return jdbcTemplate
				.queryForObject(
						"select cluster_id, cluster_type, user_id, age, weight, calories from hyg_usercluster where cluster_id = ? and user_id = ?",
						new RowMapper<UserCluster>()
						{
							public UserCluster mapRow(final ResultSet rs, final int rowNum) throws SQLException
							{
								return new UserCluster(rs.getString("cluster_id"), rs.getString("cluster_type"), rs.getString("user_id"),
										rs.getString("age"), rs.getString("weight"), rs.getString("calories"));
							}
						}, cluster_id);
	}
}
