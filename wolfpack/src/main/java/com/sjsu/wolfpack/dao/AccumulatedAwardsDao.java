package com.sjsu.wolfpack.dao;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.AccumulatedAwards;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


@Repository
public class AccumulatedAwardsDao
{
	private final JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public AccumulatedAwardsDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createAwards(final AccumulatedAwards awards) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate
					.update(
							"insert into hyg_accumulatedawards (user_id, rewards_id, retailer_id, created_date, expiry_date, reward_points) values (?, ?, ?, ?, ?, ?)",
							awards.getUserId(), awards.getRewardsId(), awards.getRetailerId(), awards.getCreatedDate(),
							awards.getExpiryDate(), awards.getRewardPoints());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(awards.getUserId());
		}
	}

}
