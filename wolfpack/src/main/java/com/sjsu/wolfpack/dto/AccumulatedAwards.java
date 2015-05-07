package com.sjsu.wolfpack.dto;

public class AccumulatedAwards
{
	private final String user_id;

	private final String rewards_id;

	private final String retailer_id;

	private final String created_date;

	private final String expiry_date;

	private final String reward_points;

	public AccumulatedAwards(final String user_id, final String rewards_id, final String retailer_id, final String created_date,
			final String expiry_date, final String reward_points)
	{
		this.user_id = user_id;
		this.rewards_id = rewards_id;
		this.retailer_id = retailer_id;
		this.created_date = created_date;
		this.expiry_date = expiry_date;
		this.reward_points = reward_points;
	}

	/**
	 * @return the user_id
	 */
	public String getUserId()
	{
		return user_id;
	}

	/**
	 * @return the rewards_id
	 */
	public String getRewardsId()
	{
		return rewards_id;
	}

	/**
	 * @return the retailer_id
	 */
	public String getRetailerId()
	{
		return retailer_id;
	}

	/**
	 * @return the created_date
	 */
	public String getCreatedDate()
	{
		return created_date;
	}

	/**
	 * @return the expiry_date
	 */
	public String getExpiryDate()
	{
		return expiry_date;
	}

	/**
	 * @return the reward_points
	 */
	public String getRewardPoints()
	{
		return reward_points;
	}
}
