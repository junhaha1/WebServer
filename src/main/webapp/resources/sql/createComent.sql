create table coment(
	CNUM INT NOT NULL AUTO_INCREMENT,
	BID INT not null,
	ID varchar(255) not null,
	content TEXT,
	REGDATE DATETIME,
	parentID varchar(255) default null,
	primary key (CNUM)
)default CHARSET=utf8;

ALTER TABLE coment ADD CONSTRAINT member_coment_fk FOREIGN KEY(ID) REFERENCES MEMBER(ID) ON UPDATE CASCADE;
ALTER TABLE coment ADD CONSTRAINT board_coment_fk FOREIGN KEY(BID) REFERENCES BOARD(BID) ON UPDATE CASCADE;