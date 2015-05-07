package com.sjsu.wolfpack.dto;

public class AssociatedRewards
{
	private final String rewardName;

	private final String clusterid;

	private final String clusterType;

	public AssociatedRewards(final String rewardName, final String clusterid, final String clusterType)
	{
		this.clusterid = clusterid;
		this.clusterType = clusterType;
		this.rewardName = rewardName;
	}

	public String getClusterId()
	{
		return clusterid;
	}

	public String getClusterType()
	{
		return clusterType;
	}

	public String getRewardName()
	{
		return rewardName;
	}
}
