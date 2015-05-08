package com.sjsu.wolfpack.dto;

import java.util.Date;

public class Race {

	private String racename;
	
	private String raceType;
	
	private Date create_date;
	
	private String raceOwner;
	
	private String raceTeams;
	
	private String racePreference;
	
	private String raceMotivation;
	
	public Race(String racename, String raceType, Date create_date, String raceOwner, String raceTeams, String racePreference, String raceMotivation){
		
		this.racename = racename;
		this.raceType = raceType;
		this.raceOwner = raceOwner;
		this.create_date = create_date;
		this.racePreference = racePreference;
		this.raceMotivation = raceMotivation;
		this.raceTeams = raceTeams;
		
	}
	
	public String getRacename() {
		return racename;
	}

	public void setRacename(String racename) {
		this.racename = racename;
	}

	public String getRaceType() {
		return raceType;
	}

	public void setRaceType(String raceType) {
		this.raceType = raceType;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getRaceOwner() {
		return raceOwner;
	}

	public void setRaceOwner(String raceOwner) {
		this.raceOwner = raceOwner;
	}

	public String getRaceTeams() {
		return raceTeams;
	}

	public void setRaceTeams(String raceTeams) {
		this.raceTeams = raceTeams;
	}

	public String getRacePreference() {
		return racePreference;
	}

	public void setRacePreference(String racePreference) {
		this.racePreference = racePreference;
	}

	public String getRaceMotivation() {
		return raceMotivation;
	}

	public void setRaceMotivation(String raceMotivation) {
		this.raceMotivation = raceMotivation;
	}


	
}
