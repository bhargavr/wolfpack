/**
 * 
 */
package com.sjsu.wolfpack.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.wolfpack.dto.Reward;


//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author bhargav
 * 
 */
@Repository
public class RewardDao
{

	private JdbcTemplate jdbcTemplate;

	//	private final PasswordEncoder passwordEncoder;

	@Inject
	public RewardDao(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		//		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	//	public void createAccount(final Account user) throws UsernameAlreadyInUseException
	public void createReward(final Reward reward)
	{
		jdbcTemplate
				.update("insert into hyg_retailrewards (sku, name, points,path,retailer,expiration) values (?,?,?,?,?,?)",
						reward.getSku(), reward.getName(), reward.getPoints(), reward.getPath(), reward.getRetailer(),
						reward.getExpiration());

	}

	public List<Reward> getAllRewards()
	{
		List<Reward> rewards = new ArrayList<Reward>();

		List<Map<String, Object>> rewardList = jdbcTemplate.queryForList("select * from hyg_retailrewards");

		for ( Map reward : rewardList)
		{
			 Reward rewardObj = new Reward(reward.get("name").toString(), reward.get("points").toString(), reward.get("sku")
					.toString(), reward.get("path").toString(), reward.get("retailer").toString(), reward.get("expiration").toString());
			rewards.add(rewardObj);
		}

		//		jdbcTemplate.queryForList("select * from hyg_retailrewards",
		//				new RowMapper<Reward>()
		//				{
		//					public void mapRow(final ResultSet rs, final int rowNum) throws SQLException
		//					{
		//						rewardList.add(new Reward(rs.getString("sku"),rs.getString("name"),rs.getString("points"),rs.getString("path")));
		//					}
		//				});

		return rewards;
	}

}
