/**
 * 
 */
package com.sjsu.wolfpack.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import com.fitbit.api.common.model.user.UserInfo;
import com.fitbit.api.model.APIResourceCredentials;
import com.sjsu.wolfpack.config.FitbitRequestContext;
import com.sjsu.wolfpack.constants.ApplicationConstants;
import com.sjsu.wolfpack.dao.AccountDao;
import com.sjsu.wolfpack.dto.Account;


//import org.springframework.core.env.Environment;

/**
 * @author bhargav
 * 
 */
@RestController
public class FetchUserProfileController
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

	private final AccountDao accountDao;

	@Inject
	public FetchUserProfileController(final AccountDao accountDao)
	{
		this.accountDao = accountDao;
	}

	@RequestMapping("/fetchProfile")
	public UserInfo fetchProfile(@RequestParam(value = "oauth_token", defaultValue = "World") final String oauth_token,
			@RequestParam(value = "oauth_verifier", defaultValue = "World") final String oauth_verifier)
	{

		final FitbitAPIClientService<FitbitApiClientAgent> apiClientService = new FitbitAPIClientService<FitbitApiClientAgent>(
				new FitbitApiClientAgent(apiBaseUrl, fitbitSiteBaseUrl, credentialsCache), clientConsumerKey, clientSecret,
				credentialsCache, entityCache, subscriptionStore);

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

		UserInfo userInfo = null;

		final APIResourceCredentials arc = new APIResourceCredentials("-", null, null);
		arc.setAccessToken(actDetails.getOauthToken());
		arc.setAccessTokenSecret(actDetails.getOauthSecret());
//		arc.setAccessToken(tempTokenReceived);
//		arc.setAccessTokenSecret(tempTokenVerifier);
		apiClientService.saveResourceCredentials(localUser, arc);
		apiClientService.getClient().getCredentialsCache().saveResourceCredentials(localUser, arc);

		try
		{
			userInfo = apiClientService.getClient().getUserInfo(localUser);

		}
		catch (final FitbitAPIException e)
		{
			e.printStackTrace();
		}


		return userInfo;
	}

}
