/**
 * 
 */
package com.sjsu.wolfpack.rest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.fitbit.api.common.model.sleep.Sleep;
import com.fitbit.api.common.service.FitbitApiService;
import com.fitbit.api.model.APIResourceCredentials;
import com.fitbit.api.model.FitbitUser;
import com.sjsu.wolfpack.constants.ApplicationConstants;

/**
 * @author bhargav
 *
 */
@RestController
public class FetchSleepController {
	
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_VERIFIER = "oauth_verifier";
    
//    @Autowired
//    private Environment env;

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
    
    @RequestMapping("/fetchSleep")
    public Sleep fetchSleep(@RequestParam(value="oauth_token", defaultValue="World") String oauth_token,
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
        
        String tempTokenReceived = oauth_token;
        String tempTokenVerifier = oauth_verifier;
        LocalUserDetail localUser = new LocalUserDetail("-");
        
        Sleep sleepInfo = null;

        APIResourceCredentials arc = new APIResourceCredentials("-", null, null);
        arc.setAccessToken(tempTokenReceived);
        arc.setAccessTokenSecret(tempTokenVerifier);
        apiClientService.saveResourceCredentials(localUser, arc);
        apiClientService.getClient().getCredentialsCache().saveResourceCredentials(localUser, arc);
        LocalDate date = FitbitApiService.getValidLocalDateOrNull(dateStr);

        try {
        	sleepInfo = apiClientService.getClient().getSleep(localUser, FitbitUser.CURRENT_AUTHORIZED_USER, date);
			
		} catch (FitbitAPIException e) {
			e.printStackTrace();
		}
    	
    	
        return sleepInfo;
    }

}
