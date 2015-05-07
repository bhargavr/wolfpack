package com.sjsu.wolfpack.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.PersonalRewards;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


@Repository
public class PersonalRewardsDao
{

	private final JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public PersonalRewardsDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createPersonalRewards(final PersonalRewards reward) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate
					.update(
							"insert into hyg_personalrewards (retailer, name, description, percentage_threshold, points) values (?, ?, ?, ?, ?)",
							reward.getRetailer(), reward.getName(), reward.getDescription(), reward.getPercentageThreshold(),
							reward.getPoints());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(reward.getRetailer());
		}
	}

	public PersonalRewards findClusterByRetailerName(final String retailer, final String name)
	{
		return jdbcTemplate.queryForObject(
				"select retailer, name, description, percentage_threshold from hyg_personalrewards where retailer = ? and name = ?",
				new RowMapper<PersonalRewards>()
				{
					public PersonalRewards mapRow(final ResultSet rs, final int rowNum) throws SQLException
					{
						return new PersonalRewards(rs.getString("retailer"), rs.getString("name"), rs.getString("description"), rs
								.getString("percentage_threshold"), rs.getString("points"));
					}
				}, retailer, name);
	}
}
