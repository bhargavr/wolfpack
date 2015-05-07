/**
 * 
 */
package com.sjsu.wolfpack.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitbit.api.client.FitbitAPIEntityCache;
import com.fitbit.api.client.FitbitApiCredentialsCache;
import com.fitbit.api.client.FitbitApiCredentialsCacheMapImpl;
import com.fitbit.api.client.FitbitApiEntityCacheMapImpl;
import com.fitbit.api.client.FitbitApiSubscriptionStorage;
import com.fitbit.api.client.FitbitApiSubscriptionStorageInMemoryImpl;
import com.fitbit.api.common.model.user.UserInfo;
import com.sjsu.wolfpack.constants.ApplicationConstants;
import com.sjsu.wolfpack.dao.AccountDao;
import com.sjsu.wolfpack.dao.RewardDao;
import com.sjsu.wolfpack.dto.Reward;


//import org.springframework.core.env.Environment;

/**
 * @author bhargav
 * 
 */
@RestController
public class NewRewardsController
{

	public static final String OAUTH_TOKEN = "oauth_token";
	public static final String OAUTH_VERIFIER = "oauth_verifier";

	//    @Autowired
	//    private Environment env;

	private final FitbitAPIEntityCache entityCache = new FitbitApiEntityCacheMapImpl();
	private final FitbitApiCredentialsCache credentialsCache = new FitbitApiCredentialsCacheMapImpl();
	private final FitbitApiSubscriptionStorage subscriptionStore = new FitbitApiSubscriptionStorageInMemoryImpl();

	//    private String apiBaseUrl = env.getProperty(ApplicationConstants.apiBaseUrl);
	//    private String fitbitSiteBaseUrl = env.getProperty(ApplicationConstants.fitbitSiteBaseUrl);
	//    private String clientConsumerKey = env.getProperty(ApplicationConstants.clientConsumerKey);
	//    private String clientSecret = env.getProperty(ApplicationConstants.clientSecret);

	private final String apiBaseUrl = ApplicationConstants.apiBaseUrl;
	private final String fitbitSiteBaseUrl = ApplicationConstants.fitbitSiteBaseUrl;
	private final String clientConsumerKey = ApplicationConstants.clientConsumerKey;
	private final String clientSecret = ApplicationConstants.clientSecret;

	private final RewardDao rewardDao;

	@Inject
	public NewRewardsController(final RewardDao rewardDao)
	{
		this.rewardDao = rewardDao;
	}
	
	@RequestMapping(value = "/createNewReward" , method = RequestMethod.POST)
    public @ResponseBody String createReward(@RequestBody String jsonString, Model model) throws JSONException, SQLException {
			
			System.out.println(jsonString);
			
			JSONObject rewardobj = new JSONObject(jsonString);
			
			/*
			 reward["sku"] = $("#rewardsku").val();
	    	reward["name"] = $("#rewardname").val();
	    	reward["points"] = $("#rewardpoints").val();
	    	reward["path"] = $("#rewardpath").val();
	    	reward["retailer"] = $("#rewardretailer").val();
	    	reward["expiration"] = $("#rewardexpiration").val();
			 */
			System.out.println(rewardobj.getString("name")+rewardobj.getString("points")+rewardobj.getString("sku")+
					rewardobj.getString("path")+rewardobj.getString("retailer")+rewardobj.getString("expiration"));
			
			Reward reward = new Reward(rewardobj.getString("name"),rewardobj.getString("points"),rewardobj.getString("sku"),
					rewardobj.getString("path"),rewardobj.getString("retailer"),rewardobj.getString("expiration"));
			  	
			rewardDao.createReward(reward);

			return "success";
    }
	
	@RequestMapping("/getRewards")
	public List<Reward> getRewards()
	{
		List<Reward> rewards= new ArrayList<Reward>();
		
		rewards = rewardDao.getAllRewards();
		
		return rewards;
	}
	
	
}
