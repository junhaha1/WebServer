alter table member add LASTDATE datetime default NULL;
alter table member add ACTIVITY INT not null default 0;



ALTER TABLE member ADD COLUMN DELDATE DATETIME default null;
ALTER TABLE member ADD COLUMN DELCODE INT default 0;
