/**
 * 
 */
package com.sjsu.wolfpack.rest;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sjsu.wolfpack.dao.AccountDao;
import com.sjsu.wolfpack.dto.Account;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;

/**
 * @author bhargav
 *
 */
public class TeamController {

	private AccountDao accountDao;

	@Inject
	public TeamController(AccountDao accountDao)
	{
		this.accountDao = accountDao;
	}
	
    @RequestMapping(value = "/teamController", method = RequestMethod.POST)
    public ModelAndView registerController(@RequestBody String jsonString, Model model) throws JSONException {
    	
		System.out.println(jsonString);
		
		JSONObject teamobj = new JSONObject(jsonString);
    	
    		Account registerUser = new Account(teamobj.getString("teamName"), teamobj.getString("password"), teamobj.getString("displayName"), 0, null, null, null, null, null, null, null);

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
