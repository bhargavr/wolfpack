/**
 * 
 */
package com.sjsu.wolfpack.dto;

/**
 * @author bhargav
 * 
 */
public class Account
{

	private String username;

	private String password;

	private String displayName;

	private int user_id;

	private String oauthToken;

	private String oauthSecret;

	private String cluster;

	private String personalReward;

	private String communityReward;

	private String predicted_avg;
	
	private String wr_d_id;

	public Account( String username,  String password,  String displayName, int user_id,
			 String oauthToken,  String oauthSecret,  String cluster, String personalReward,
			 String communityReward, String predicted_avg, String wr_d_id)
	{
		this.username = username;
		this.password = password;
		this.displayName = displayName;
		this.user_id = user_id;
		this.oauthToken = oauthToken;
		this.oauthSecret = oauthSecret;
		this.cluster = cluster;
		this.personalReward = personalReward;
		this.communityReward = communityReward;
		this.predicted_avg = predicted_avg;
		this.wr_d_id = wr_d_id;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * @return the user_id
	 */
	public int getUser_id()
	{
		return user_id;
	}

	/**
	 * @return the oauthToken
	 */
	public String getOauthToken()
	{
		return oauthToken;
	}

	/**
	 * @return the oauthSecret
	 */
	public String getOauthSecret()
	{
		return oauthSecret;
	}

	/**
	 * @return the cluster
	 */
	public String getCluster()
	{
		return cluster;
	}

	/**
	 * @return the personalReward
	 */
	public String getPersonalReward()
	{
		return personalReward;
	}

	/**
	 * @return the communityReward
	 */
	public String getCommunityReward()
	{
		return communityReward;
	}

	/**
	 * @return the predicited_avg
	 */
	public String getPredicted_Avg()
	{
		return predicted_avg;
	}
	/**
	 * @return the user_id
	 */
	public String getWr_d_id()
	{
		return wr_d_id;
	}

}
