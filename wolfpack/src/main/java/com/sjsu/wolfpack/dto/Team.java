/**
 * 
 */
package com.sjsu.wolfpack.dto;

import java.util.Date;

/**
 * @author bhargav
 *
 */
public class Team {
	
	private String teamname;
	
	private String level;
	
	private Date create_date;
	
	private String teamLeader;
	
	private String teamMembers;
	
	private String teamPreference;
	
	private String teamMotivation;
	
	public Team(String teamname, String level, Date create_date, String teamLeader, String teamMemebers, String teamPreference, String teamMotivation){
		
		this.teamname = teamname;
		this.level = level;
		this.teamLeader = teamLeader;
		this.create_date = create_date;
		this.teamPreference = teamPreference;
		this.teamMotivation = teamMotivation;
		this.teamMembers = teamMemebers;
		
	}
	
	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getTeamPreference() {
		return teamPreference;
	}

	public void setTeamPreference(String teamPreference) {
		this.teamPreference = teamPreference;
	}

	public String getTeamMotivation() {
		return teamMotivation;
	}

	public void setTeamMotivation(String teamMotivation) {
		this.teamMotivation = teamMotivation;
	}

}
