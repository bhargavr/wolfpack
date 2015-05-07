package com.sjsu.wolfpack.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.wolfpack.dao.ClusterDao;
import com.sjsu.wolfpack.dao.RewardDao;
import com.sjsu.wolfpack.dto.Cluster;
import com.sjsu.wolfpack.dto.Reward;


//import org.springframework.core.env.Environment;
@RestController
public class FetchModelController
{

	private final ClusterDao clusterDao;
	private final RewardDao rewardDao;

	@Inject
	public FetchModelController(final ClusterDao clusterDao, final RewardDao rewardDao)
	{
		this.clusterDao = clusterDao;
		this.rewardDao = rewardDao;
	}

	@RequestMapping("/fetchClusters")
	public List<Cluster> fetchClusters()
	{
		final List<Cluster> clusters = clusterDao.getClusters();
		return clusters;
	}

	@RequestMapping("/fetchRewards")
	public List<Reward> fetchRewards()
	{
		final List<Reward> rewards = rewardDao.getAllRewards();

		return rewards;
	}

}
