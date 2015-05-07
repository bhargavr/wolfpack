  CREATE  TABLE hyg_user (
  user_id int NOT NULL AUTO_INCREMENT,
  create_date DATE NULL,
  userName VARCHAR(80) NOT NULL,
  password VARCHAR(80) NOT NULL,
  oauthToken VARCHAR(120) NULL,
  oauthSecret VARCHAR(120) NULL,
  displayName VARCHAR(120) NULL,
  cluster VARCHAR(120) NULL, 
  personal_reward VARCHAR(120) NULL, 
  community_reward VARCHAR(120) NULL, 
  predicted_avg VARCHAR(120) NULL,
  wr_d_id VARCHAR(120) NULL,
  PRIMARY KEY (user_id),
  UNIQUE (userName));
  
  CREATE  TABLE team (
  team_id int NOT NULL AUTO_INCREMENT,
  create_date DATE NULL,
  teamName VARCHAR(80) NOT NULL,
  level VARCHAR(80) NOT NULL,
  teamLeader VARCHAR(120) NULL,
  teamMembers VARCHAR(120) NULL,
  PRIMARY KEY (team_id),
  UNIQUE (teamName));
  
  CREATE  TABLE race (
  race_id int NOT NULL AUTO_INCREMENT,
  create_date DATE NULL,
  raceName VARCHAR(80) NOT NULL,
  raceType VARCHAR(80) NOT NULL,
  raceOwner VARCHAR(120) NULL,
  raceTeams VARCHAR(120) NULL,
  PRIMARY KEY (race_id),
  UNIQUE (raceName));
  
  CREATE  TABLE date_dim (
  date_id DATE NULL,
  week int(2) NULL,
  month VARCHAR(80) NOT NULL,
  year int(4) NOT NULL,
  date_ui VARCHAR(120) NULL, 
  PRIMARY KEY (date_id));
  
  CREATE  TABLE stats_fact (
  stat_id int NOT NULL AUTO_INCREMENT,
  stat_type VARCHAR(80) NOT NULL,
  stat_value int(80) NOT NULL,
  date_id DATE NOT NULL,
  race_id int NULL,
  team_id int NULL,
  user_id int NOT NULL,
  PRIMARY KEY (stat_id));
  
  