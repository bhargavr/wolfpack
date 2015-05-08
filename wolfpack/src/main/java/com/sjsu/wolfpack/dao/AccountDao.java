/**
 * 
 */
package com.sjsu.wolfpack.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.Account;
import com.sjsu.wolfpack.dto.Race;
import com.sjsu.wolfpack.dto.Reward;
import com.sjsu.wolfpack.dto.Team;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author bhargav
 * 
 */
@Repository
public class AccountDao
{

	private JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public AccountDao(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createAccount(Account user) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate
					.update(
							"insert into hyg_user (user_id,create_date, userName, password, oauthToken, oauthSecret, displayName, cluster, personal_reward, community_reward, predicted_avg, wr_d_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
							user.getUser_id(), new Date(), user.getUsername(), user.getPassword(), user.getOauthToken(),
							user.getOauthSecret(), user.getDisplayName(), user.getCluster(), user.getPersonalReward(),
							user.getCommunityReward(), user.getPredicted_Avg(), user.getWr_d_id());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(user.getUsername());
		}
	}

	public Account findAccountByUsername(String userName)
	{
		return jdbcTemplate.queryForObject("select userName, displayName, user_id, oauthToken, oauthSecret from hyg_user where userName = ?",
				new RowMapper<Account>()
				{
					public Account mapRow(ResultSet rs, int rowNum) throws SQLException
					{
						return new Account(rs.getString("userName"), null, rs.getString("displayName"), rs.getInt("user_id"), rs.getString("oauthToken"),
								rs.getString("oauthSecret"), null, null, null, null, null);
					}
				}, userName);
	}
	
	public int updateByUsername(String oauthToken, String oauthSecret, String userName)
	{
		return jdbcTemplate.update("update hyg_user set oauthToken = ?, oauthSecret = ? where userName = ?",oauthToken, oauthSecret,userName);
	}
	
	public List<Account> getAllAccounts(String userName)
	{
		final List<Map<String, Object>> accountList = jdbcTemplate.queryForList("select * from hyg_user");
		
		List<Account> userList = new ArrayList<Account>();

		for (final Map account : accountList)
		{
			 Account accountObj = new Account(account.get("userName").toString(), null, account.get("displayName").toString(), (Integer)account.get("user_id"), null,
						null, null, null, null, null, account.get("wr_d_id").toString());
			 userList.add(accountObj);
			}
		
	 return userList;
	 
	}
	
	@Transactional
	public void createTeam(Race race) throws UsernameAlreadyInUseException
	{
		try
		{
			jdbcTemplate
					.update(
							"insert into hyg_user (create_date, raceName, raceType, raceOwner, raceTeams, racePreference, raceMotivation) values ( ?, ?, ?, ?, ?, ?, ?)",
							 new Date(),race.getRacename(),race.getRaceType(),race.getRaceOwner(),race.getRaceTeams(),race.getRacePreference(),race.getRaceMotivation());
		}
		catch (final DuplicateKeyException e)
		{
			throw new UsernameAlreadyInUseException(race.getRacename());
		}
	}
	

}
