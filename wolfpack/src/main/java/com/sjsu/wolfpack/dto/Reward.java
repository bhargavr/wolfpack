/**
 * 
 */
package com.sjsu.wolfpack.dto;

/**
 * @author ankur
 * 
 */
public class Reward
{

	private String name;

	private String points;

	private String sku;

	private String path;
	
	private String retailer;
	
	private String expiration;

	public Reward(String name,String points,String sku,String path,String retailer, String expiration)
	{
		this.name = name;
		this.points = points;
		this.sku = sku;
		this.path = path;
		this.retailer = retailer;
		this.expiration = expiration;
	}
	public String getName()
	{
		return name;
	}
	public String getPoints()
	{
		return points;
	}
	public String getSku()
	{
		return sku;
	}
	public String getPath()
	{
		return path;
	}
	
	public String getRetailer()
	{
		return retailer;
	}
	
	public String getExpiration()
	{
		return expiration;
	}

}
