drop table if exists Statistics;
drop table if exists Player;
drop table if exists Trainer;


create table Player(
 firstName VARCHAR,
 lastName VARCHAR,
 age INTEGER,
 countryOfBirth VARCHAR,
 position VARCHAR,
 PRIMARY KEY (firstName,lastName)
);

create table Trainer(
 firstName VARCHAR,
 lastName VARCHAR,
 age INTEGER,
 PRIMARY KEY (firstName,lastName)
);