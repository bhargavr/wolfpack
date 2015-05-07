package com.sjsu.wolfpack.dto;

public class UserCluster
{
	private final String clusterid;

	private final String clusterType;

	private final String user_id;

	private final String age;

	private final String weight;

	private final String calories;

	public UserCluster(final String clusterid, final String clusterType, final String user_id, final String age,
			final String weight, final String calories)
	{
		this.clusterid = clusterid;
		this.clusterType = clusterType;
		this.user_id = user_id;
		this.age = age;
		this.weight = weight;
		this.calories = calories;
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
	 * @return the user_id
	 */
	public String getUser_Id()
	{
		return user_id;
	}

	/**
	 * @return the age
	 */
	public String getAge()
	{
		return age;
	}

	/**
	 * @return the weight
	 */
	public String getWeight()
	{
		return weight;
	}

	/**
	 * @return the calories
	 */
	public String getCalories()
	{
		return calories;
	}


}
