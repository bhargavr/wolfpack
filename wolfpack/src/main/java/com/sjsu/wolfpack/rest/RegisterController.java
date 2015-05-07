/**
 * 
 */
package com.sjsu.wolfpack.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.fitbit.api.common.model.activities.Activities;
import com.fitbit.api.common.service.FitbitApiService;
import com.fitbit.api.model.APIResourceCredentials;
import com.fitbit.api.model.FitbitUser;
import com.sjsu.wolfpack.constants.ApplicationConstants;
import com.sjsu.wolfpack.dao.AccountDao;
import com.sjsu.wolfpack.dto.Account;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;

/**
 * @author bhargav
 *
 */
@RestController
public class RegisterController {
    

	private AccountDao accountDao;

	@Inject
	public RegisterController(AccountDao accountDao)
	{
		this.accountDao = accountDao;
	}
	
    @RequestMapping(value = "/registerController", method = RequestMethod.POST)
    public ModelAndView registerController(@RequestBody String jsonString, Model model) throws JSONException {
    	
		System.out.println(jsonString);
		
		JSONObject userobj = new JSONObject(jsonString);
    	
    		Account registerUser = new Account(userobj.getString("userName"), userobj.getString("password"), userobj.getString("displayName"), 0, null, null, null, null, null, null, null);

			try
			{
				accountDao.createAccount(registerUser);
			}
			catch (UsernameAlreadyInUseException e)
			{
				e.printStackTrace();
			}
    	
    	
    	return new ModelAndView("redirect:login");
    }

}
