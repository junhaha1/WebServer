create table BOARDSTATE(
	BID INT NOT NULL, 
	ICOUNT INT default 0,
	ECOUNT INT default 0,
	NCOUNT INT default 0,
	SCOUNT INT default 0,
	TCOUNT INT default 0,
	FCOUNT INT default 0,
	JCOUNT INT default 0,
	PCOUNT INT default 0,
	primary key(BID)
)default CHARSET=utf8;

ALTER TABLE BOARDSTATE ADD CONSTRAINT boardstate_board_fk FOREIGN KEY(BID) REFERENCES BOARD(BID) ON DELETE CASCADE;
