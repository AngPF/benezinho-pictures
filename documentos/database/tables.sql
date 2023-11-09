--------------------------------------------------------
--  DDL for Table TB_GENRE
--------------------------------------------------------

CREATE TABLE "TB_GENRE"
(	"ID_GENRE" NUMBER(19,0),
     "NM_GENRE" VARCHAR2(255 CHAR)
    ) ;


CREATE UNIQUE INDEX  "IDX_TB_GENRE_ID" ON  "TB_GENRE" ("ID_GENRE");
--------------------------------------------------------
--  Constraints for Table TB_GENRE
--------------------------------------------------------

ALTER TABLE  "TB_GENRE" MODIFY ("ID_GENRE" NOT NULL ENABLE);
ALTER TABLE  "TB_GENRE" ADD PRIMARY KEY ("ID_GENRE");

--------------------------------------------------------
--  DDL for Table TB_MOVIE
--------------------------------------------------------

CREATE TABLE  "TB_MOVIE"
(	"ID_MOVIE" NUMBER(19,0),
     "ADULT" NUMBER(1,0),
     "ORIGINALLANGUAGE" VARCHAR2(255 CHAR),
	"TITLE" VARCHAR2(255 CHAR),
	"GENRE" NUMBER(19,0)
   )  ;


CREATE UNIQUE INDEX  "IDX_TB_MOVIE_ID" ON  "TB_MOVIE" ("ID_MOVIE");
--------------------------------------------------------
--  Constraints for Table TB_MOVIE
--------------------------------------------------------

ALTER TABLE  "TB_MOVIE" MODIFY ("ID_MOVIE" NOT NULL ENABLE);
ALTER TABLE  "TB_MOVIE" MODIFY ("ADULT" NOT NULL ENABLE);
ALTER TABLE  "TB_MOVIE" ADD CHECK (adult in (0,1)) ENABLE;
ALTER TABLE  "TB_MOVIE" ADD PRIMARY KEY ("ID_MOVIE");
--------------------------------------------------------
--  Ref Constraints for Table TB_MOVIE
--------------------------------------------------------

ALTER TABLE  "TB_MOVIE" ADD CONSTRAINT "TB_MOVIE_FK_GENRE" FOREIGN KEY ("GENRE")
    REFERENCES  "TB_GENRE" ("ID_GENRE") ENABLE;

--------------------------------------------------------
--  DDL for Sequence SQ_GENRE
--------------------------------------------------------

CREATE SEQUENCE  "SQ_GENRE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

--------------------------------------------------------
--  DDL for Sequence SQ_MOVIE
--------------------------------------------------------

CREATE SEQUENCE  "SQ_MOVIE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
