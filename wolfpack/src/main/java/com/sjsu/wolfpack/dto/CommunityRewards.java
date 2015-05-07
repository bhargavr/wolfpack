package com.sjsu.wolfpack.dto;

public class CommunityRewards
{
	private final String retailer;

	private final String name;

	private final String description;

	private final String clusterid;

	private final String min;

	private final String max;

	private final String points;

	public CommunityRewards(final String retailer, final String name, final String description, final String clusterid,
			final String min, final String max, final String points)
	{
		this.retailer = retailer;
		this.name = name;
		this.description = description;
		this.clusterid = clusterid;
		this.min = min;
		this.max = max;
		this.points = points;
	}

	/**
	 * @return the retailer
	 */
	public String getRetailer()
	{
		return retailer;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the clusterid
	 */
	public String getClusterid()
	{
		return clusterid;
	}

	/**
	 * @return the cluster_id
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the min
	 */
	public String getMin()
	{
		return min;
	}

	/**
	 * @return the max
	 */
	public String getMax()
	{
		return max;
	}

	/**
	 * @return the points
	 */
	public String getPoints()
	{
		return points;
	}
}
