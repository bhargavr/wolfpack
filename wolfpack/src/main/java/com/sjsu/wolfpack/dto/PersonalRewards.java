package com.sjsu.wolfpack.dto;

public class PersonalRewards
{
	private final String retailer;

	private final String name;

	private final String description;

	private final String percentageThreshold;

	private final String points;

	public PersonalRewards(final String retailer, final String name, final String description, final String percentageThreshold,
			final String points)
	{
		this.retailer = retailer;
		this.name = name;
		this.description = description;
		this.percentageThreshold = percentageThreshold;
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
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the percentageThreshold
	 */
	public String getPercentageThreshold()
	{
		return percentageThreshold;
	}

	/**
	 * @return the percentageThreshold
	 */
	public String getPoints()
	{
		return points;
	}

}
