create table webproject.MEMBER (
    ID varchar(255) NOT NULL,
    PASSWORD varchar(100) NOT NULL,
    EMAIL varchar(255) NOT NULL,
    NAME varchar(100) NOT NULL,
    REGDATE datetime,
    MBTI varchar(4),
    primary key(ID),
    unique key (EMAIL) 
) default CHARSET = utf8;