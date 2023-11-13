--------------------------------------------------------
--  DDL for Table TB_CLIENTE
--------------------------------------------------------

CREATE TABLE "TB_CLIENTE"
(	"ID_CLIENTE" NUMBER(19,0),
     "NM_CLIENTE" VARCHAR2(255 CHAR)
) ;


CREATE UNIQUE INDEX  "IDX_TB_CLIENTE_ID" ON  "TB_CLIENTE" ("ID_CLIENTE");
--------------------------------------------------------
--  Constraints for Table TB_CLIENTE
--------------------------------------------------------

ALTER TABLE  "TB_CLIENTE" MODIFY ("ID_CLIENTE" NOT NULL ENABLE);
ALTER TABLE  "TB_CLIENTE" ADD PRIMARY KEY ("ID_CLIENTE");

--------------------------------------------------------
--  DDL for Table TB_BICICLETA
--------------------------------------------------------

CREATE TABLE  "TB_BICICLETA"
(	"ID_BICICLETA" NUMBER(19,0),
     "DESCRICAO" VARCHAR2(255 CHAR),
     "CLIENTE" NUMBER(19,0)
)  ;


CREATE UNIQUE INDEX  "IDX_TB_BICICLETA_ID" ON  "TB_BICICLETA" ("ID_BICICLETA");
--------------------------------------------------------
--  Constraints for Table TB_BICICLETA
--------------------------------------------------------

ALTER TABLE  "TB_BICICLETA" MODIFY ("ID_BICICLETA" NOT NULL ENABLE);
ALTER TABLE  "TB_BICICLETA" ADD PRIMARY KEY ("ID_BICICLETA");
--------------------------------------------------------
--  Ref Constraints for Table TB_BICICLETA
--------------------------------------------------------

ALTER TABLE  "TB_BICICLETA" ADD CONSTRAINT "TB_BICICLETA_FK_CLIENTE" FOREIGN KEY ("CLIENTE")
    REFERENCES  "TB_CLIENTE" ("ID_CLIENTE") ENABLE;

--------------------------------------------------------
--  DDL for Sequence SQ_CLIENTE
--------------------------------------------------------

CREATE SEQUENCE  "SQ_CLIENTE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

--------------------------------------------------------
--  DDL for Sequence SQ_BICICLETA
--------------------------------------------------------

CREATE SEQUENCE  "SQ_BICICLETA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;