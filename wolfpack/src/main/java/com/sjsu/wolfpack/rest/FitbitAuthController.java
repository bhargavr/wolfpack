/**
 * 
 */
package com.sjsu.wolfpack.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

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
import com.fitbit.api.model.APIResourceCredentials;
import com.sjsu.wolfpack.config.FitbitRequestContext;
import com.sjsu.wolfpack.constants.ApplicationConstants;
import com.sjsu.wolfpack.dao.AccountDao;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;

/**
 * @author bhargav
 *
 */
@RestController
public class FitbitAuthController {
    
    private FitbitAPIClientService<FitbitApiClientAgent> apiClientService;
	private final String apiBaseUrl = ApplicationConstants.apiBaseUrl;
	private final String fitbitSiteBaseUrl = ApplicationConstants.fitbitSiteBaseUrl;
	private final String clientConsumerKey = ApplicationConstants.clientConsumerKey;
	private final String clientSecret = ApplicationConstants.clientSecret;
	private final FitbitAPIEntityCache entityCache = new FitbitApiEntityCacheMapImpl();
	private final FitbitApiCredentialsCache credentialsCache = new FitbitApiCredentialsCacheMapImpl();
	private final FitbitApiSubscriptionStorage subscriptionStore = new FitbitApiSubscriptionStorageInMemoryImpl();
	
	private AccountDao accountDao;

	@Inject
	public FitbitAuthController(AccountDao accountDao)
	{
		this.accountDao = accountDao;
	}
    
    @RequestMapping("/authorizeFitbit")
    public ModelAndView showAuthorize(HttpServletRequest request, HttpServletResponse response) {
    	FitbitRequestContext context = new FitbitRequestContext();
        apiClientService = new FitbitAPIClientService<FitbitApiClientAgent>(
				new FitbitApiClientAgent(apiBaseUrl, fitbitSiteBaseUrl, credentialsCache), clientConsumerKey, clientSecret,
				credentialsCache, entityCache, subscriptionStore);
        context.setApiClientService(apiClientService);
//        context.setOurUser(new LocalUserDetail(String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()))));
        context.setOurUser(new LocalUserDetail("-"));
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        session.setAttribute("FitbitRequestContext", context);
        
        try {
            // Redirect to page where user can authorize the application:
        	return new ModelAndView("redirect:" + context.getApiClientService().getResourceOwnerAuthorizationURL(context.getOurUser(), ApplicationConstants.baseUrl+"/completeFitbitAuthorization"));
        } catch (FitbitAPIException e) {
            request.setAttribute("errors", Collections.singletonList(e.getMessage()));
            e.printStackTrace();
        }
        
        return new ModelAndView("redirect:home");
    }
    
    @RequestMapping("/completeFitbitAuthorization")
    public ModelAndView showCompleteAuthorization(HttpServletRequest request, HttpServletResponse response) {
        String tempTokenReceived = request.getParameter(ApplicationConstants.OAUTH_TOKEN);
        String tempTokenVerifier = request.getParameter(ApplicationConstants.OAUTH_VERIFIER);

        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        
//        session.setAttribute(ApplicationConstants.OAUTH_TOKEN, tempTokenReceived);
//        session.setAttribute(ApplicationConstants.OAUTH_VERIFIER, tempTokenVerifier);
        
		FitbitRequestContext context = (FitbitRequestContext) session.getAttribute("FitbitRequestContext");

        APIResourceCredentials resourceCredentials = context.getApiClientService().getResourceCredentialsByTempToken(tempTokenReceived);
        


        if (resourceCredentials == null) {
            request.setAttribute("errors", "Unrecognized temporary token when attempting to complete authorization: " + tempTokenReceived);
        } else {
            // Get token credentials only if necessary:
            if (!resourceCredentials.isAuthorized()) {
                // The verifier is required in the request to get token credentials:
                resourceCredentials.setTempTokenVerifier(tempTokenVerifier);
                try {
                    // Get token credentials for user:
                    context.getApiClientService().getTokenCredentials(new LocalUserDetail(resourceCredentials.getLocalUserId()));
                } catch (FitbitAPIException e) {
                    request.setAttribute("errors", Collections.singletonList(e.getMessage()));
                }
            }
        }

        session= attr.getRequest().getSession(true);
        session.setAttribute(ApplicationConstants.OAUTH_TOKEN, resourceCredentials.getAccessToken());
        session.setAttribute(ApplicationConstants.OAUTH_VERIFIER, resourceCredentials.getAccessTokenSecret());
        
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
		accountDao.updateByUsername(resourceCredentials.getAccessToken(), resourceCredentials.getAccessTokenSecret(), user.getUsername());

        return new ModelAndView("redirect: /home");
    }
    
}
