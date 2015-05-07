package com.sjsu.wolfpack.dto;

public class Cluster
{
	private final String clusterid;

	private final String clusterType;

	private final String min;

	private final String max;

	public Cluster(final String clusterid, final String clusterType, final String min, final String max)
	{
		this.clusterid = clusterid;
		this.clusterType = clusterType;
		this.min = min;
		this.max = max;
	}

	/**
	 * @return the clusterid
	 */
	public String getClusterid()
	{
		return clusterid;
	}

	/**
	 * @return the clusterType
	 */
	public String getClusterType()
	{
		return clusterType;
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

}
