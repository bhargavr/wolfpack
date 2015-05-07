package com.sjsu.wolfpack.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.Cluster;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


@Repository
public class ClusterDao
{

	private final JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public ClusterDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createCluster(final Cluster cluster) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate.update("insert into hyg_cluster (cluster_id, cluster_type, min, max) values (?, ?, ?, ?)",
					cluster.getClusterid(), cluster.getClusterType(), cluster.getMin(), cluster.getMax());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(cluster.getClusterid());
		}
	}

	public Cluster findClusterById(final String cluster_id)
	{
		return jdbcTemplate.queryForObject("select cluster_id, cluster_type, min, max from hyg_cluster where cluster_id = ?",
				new RowMapper<Cluster>()
				{
					public Cluster mapRow(final ResultSet rs, final int rowNum) throws SQLException
					{
						return new Cluster(rs.getString("cluster_id"), rs.getString("cluster_type"), rs.getString("min"), rs
								.getString("min"));
					}
				}, cluster_id);
	}

	public List<Cluster> getClusters()
	{
		final List<Cluster> clusters = new ArrayList<Cluster>();

		final List<Map<String, Object>> clusterList = jdbcTemplate.queryForList("select * from hyg_cluster");

		for (final Map cluster : clusterList)
		{
			final Cluster clusterObj = new Cluster(cluster.get("clusterid").toString(), cluster.get("clusterType").toString(),
					cluster.get("min").toString(), cluster.get("max").toString());
			clusters.add(clusterObj);
		}

		return clusters;
	}

}
