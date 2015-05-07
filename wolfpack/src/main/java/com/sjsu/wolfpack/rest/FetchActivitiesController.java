/**
 * 
 */
package com.sjsu.wolfpack.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
//import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fitbit.api.FitbitAPIException;
import com.fitbit.api.client.FitbitAPIEntityCache;
import com.fitbit.api.client.FitbitApiClientAgent;
import com.fitbit.api.client.FitbitApiCredentialsCache;
import com.fitbit.api.client.FitbitApiCredentialsCacheMapImpl;
import com.fitbit.api.client.FitbitApiEntityCacheMapImpl;
import com.fitbit.api.client.FitbitApiSubscriptionStorage;
import com.fitbit.api.client.FitbitApiSubscriptionStorageInMemoryImpl;
import com.fitbit.api.client.LocalUserDetail;
import com.fitbit.api.client.service.FitbitAPIClientService;
import com.fitbit.api.common.model.activities.Activities;
import com.fitbit.api.common.model.user.UserInfo;
import com.fitbit.api.common.service.FitbitApiService;
import com.fitbit.api.model.APIResourceCredentials;
import com.fitbit.api.model.FitbitUser;
import com.sjsu.wolfpack.constants.ApplicationConstants;
import com.sjsu.wolfpack.dao.AccountDao;
import com.sjsu.wolfpack.dto.Account;

/**
 * @author bhargav
 *
 */
@RestController
public class FetchActivitiesController {
    
//    @Autowired
//    private Environment env;

	private final AccountDao accountDao;

	@Inject
	public FetchActivitiesController( AccountDao accountDao)
	{
		this.accountDao = accountDao;
	}
	
    private FitbitAPIEntityCache entityCache = new FitbitApiEntityCacheMapImpl();
    private FitbitApiCredentialsCache credentialsCache = new FitbitApiCredentialsCacheMapImpl();
    private FitbitApiSubscriptionStorage subscriptionStore = new FitbitApiSubscriptionStorageInMemoryImpl();

//    private String apiBaseUrl = env.getProperty(ApplicationConstants.apiBaseUrl);
//    private String fitbitSiteBaseUrl = env.getProperty(ApplicationConstants.fitbitSiteBaseUrl);
//    private String clientConsumerKey = env.getProperty(ApplicationConstants.clientConsumerKey);
//    private String clientSecret = env.getProperty(ApplicationConstants.clientSecret);
    
    private String apiBaseUrl = ApplicationConstants.apiBaseUrl;
    private String fitbitSiteBaseUrl = ApplicationConstants.fitbitSiteBaseUrl;
    private String clientConsumerKey = ApplicationConstants.clientConsumerKey;
    private String clientSecret = ApplicationConstants.clientSecret;
    
    @RequestMapping("/fetchActivities")
    public Activities fetchActivities(@RequestParam(value="oauth_token", defaultValue="World") String oauth_token,
    		@RequestParam(value="oauth_verifier", defaultValue="World") String oauth_verifier,
    		@RequestParam(value="dateStr", defaultValue="2014-06-01") String dateStr) {
    	
        FitbitAPIClientService<FitbitApiClientAgent> apiClientService = new FitbitAPIClientService<FitbitApiClientAgent>(
                new FitbitApiClientAgent(apiBaseUrl, fitbitSiteBaseUrl, credentialsCache),
                clientConsumerKey,
                clientSecret,
                credentialsCache,
                entityCache,
                subscriptionStore
        );
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
      
	      User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	      Account actDetails = accountDao.findAccountByUsername(user.getUsername());
		
		String tempTokenReceived = (String) session.getAttribute(ApplicationConstants.OAUTH_TOKEN);
		String tempTokenVerifier = (String) session.getAttribute(ApplicationConstants.OAUTH_VERIFIER);
//		FitbitRequestContext context = (FitbitRequestContext) session.getAttribute("FitbitRequestContext");
		
//		final FitbitAPIClientService<FitbitApiClientAgent> apiClientService = context.getApiClientService();
		
//		final String tempTokenReceived = oauth_token;
//		final String tempTokenVerifier = oauth_verifier;
		final LocalUserDetail localUser = new LocalUserDetail("-");
//		final LocalUserDetail localUser = context.getOurUser();



		final APIResourceCredentials arc = new APIResourceCredentials("-", null, null);
		arc.setAccessToken(actDetails.getOauthToken());
		arc.setAccessTokenSecret(actDetails.getOauthSecret());
//		arc.setAccessToken(tempTokenReceived);
//		arc.setAccessTokenSecret(tempTokenVerifier);
		apiClientService.saveResourceCredentials(localUser, arc);
		apiClientService.getClient().getCredentialsCache().saveResourceCredentials(localUser, arc);
		

        
        Activities activityInfo = null;
//        LocalDate date = FitbitApiService.getValidLocalDateOrNull(dateStr);
        LocalDate date = FitbitApiService.getValidLocalDateOrNull("2014-12-01");

        try {
        	activityInfo = apiClientService.getClient().getActivities(localUser, FitbitUser.CURRENT_AUTHORIZED_USER, date);
			
		} catch (FitbitAPIException e) {
			e.printStackTrace();
		}
    	
    	
        return activityInfo;
    }

}
