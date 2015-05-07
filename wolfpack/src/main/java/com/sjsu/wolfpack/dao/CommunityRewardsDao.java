package com.sjsu.wolfpack.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.CommunityRewards;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


@Repository
public class CommunityRewardsDao
{
	private final JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public CommunityRewardsDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createCommunityRewards(final CommunityRewards reward) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate
					.update(
							"insert into hyg_communityrewards (retailer, name, description, clusterid, max, min, points) values (?, ?, ?, ?, ?, ?, ?)",
							reward.getRetailer(), reward.getName(), reward.getDescription(), reward.getClusterid(), reward.getMax(),
							reward.getMin(), reward.getPoints());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(reward.getRetailer());
		}
	}

	public CommunityRewards findClusterByRetailerName(final String retailer, final String name)
	{
		return jdbcTemplate.queryForObject(
				"select retailer, name, description, clusterid, max, min from hyg_communityrewards where retailer = ? and name = ?",
				new RowMapper<CommunityRewards>()
				{
					public CommunityRewards mapRow(final ResultSet rs, final int rowNum) throws SQLException
					{
						return new CommunityRewards(rs.getString("retailer"), rs.getString("name"), rs.getString("description"), rs
								.getString("clusterid"), rs.getString("min"), rs.getString("max"), rs.getString("points"));
					}
				}, retailer, name);
	}


}
