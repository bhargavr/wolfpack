/**
 * 
 */
package com.sjsu.wolfpack.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author bhargav
 *
 */
public class WeeklyRewardsTask {
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        System.out.println("Weekly Rewards thread time is now " + dateFormat.format(new Date()));
    }

}
