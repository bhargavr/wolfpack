package com.sjsu.wolfpack.dao;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.AssociatedRewards;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


@Repository
public class AssociatedRewardsDao
{

	private final JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public AssociatedRewardsDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createAssociatedAwards(final AssociatedRewards awards) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate.update("insert into hyg_associatedawards (clusterid, clustertype, rewardname) values (?, ?, ?)",
					awards.getClusterId(), awards.getClusterType(), awards.getRewardName());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(awards.getClusterId());
		}
	}
}
