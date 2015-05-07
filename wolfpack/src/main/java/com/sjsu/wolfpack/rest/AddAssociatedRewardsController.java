package com.sjsu.wolfpack.rest;

import java.sql.SQLException;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjsu.wolfpack.dao.AssociatedRewardsDao;
import com.sjsu.wolfpack.dto.AssociatedRewards;
import com.sjsu.wolfpack.exception.UsernameAlreadyInUseException;


public class AddAssociatedRewardsController
{

	private final AssociatedRewardsDao awardDao;

	@Inject
	public AddAssociatedRewardsController(final AssociatedRewardsDao awardDao)
	{
		this.awardDao = awardDao;
	}

	@RequestMapping(value = "/createAssociatedReward", method = RequestMethod.POST)
	public @ResponseBody
	String insertAssociatedReward(@RequestBody final String jsonString, final AssociatedRewards reward) throws JSONException,
			SQLException
	{

		final JSONObject rewardobj = new JSONObject(jsonString);

		//final AssociatedRewards award = new AssociatedRewards(clusterid, clustertype, rewardname);

		final AssociatedRewards AssociatedRewards = new AssociatedRewards(rewardobj.getString("clusterid"),
				rewardobj.getString("clustertype"), rewardobj.getString("rewardname"));

		try
		{
			awardDao.createAssociatedAwards(AssociatedRewards);

		}
		catch (final UsernameAlreadyInUseException e)
		{
			e.printStackTrace();
		}

		return "success";


	}
}
