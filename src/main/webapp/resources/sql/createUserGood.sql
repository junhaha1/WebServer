create table USERGOOD(
	BID INT NOT NULL, 
	ID varchar(255) NOT NULL,
	primary key(BID, ID)
)default CHARSET=utf8;

ALTER TABLE USERGOOD ADD CONSTRAINT usergood_board_fk FOREIGN KEY(BID) REFERENCES BOARD(BID) ON DELETE CASCADE;
ALTER TABLE USERGOOD ADD CONSTRAINT usergood_member_fk FOREIGN KEY(ID) REFERENCES MEMBER(ID) ON DELETE CASCADE;