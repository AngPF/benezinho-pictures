--------------------------------------------------------
--  DDL for Table T_BICICLETA
--------------------------------------------------------

CREATE TABLE "T_BICICLETA"
(	"ID_BICICLETA" NUMBER(*,0),
     "MARCA" VARCHAR2(255 BYTE),
     "DT_MODELO_BICICLETA" DATE,
     "VL_BICICLETA_COR" VARCHAR2(255 BYTE),
     "PECAS_BICICLETA" VARCHAR2(255 BYTE),
     "FOTOS" BLOB,
     "ID_CLIENTE" NUMBER(*,0),
     "ID_SEGURO" NUMBER(*,0)
) SEGMENT CREATION DEFERRED
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
    NOCOMPRESS LOGGING
    TABLESPACE "TBSPC_ALUNOS"
    LOB ("FOTOS") STORE AS SECUREFILE (
    TABLESPACE "TBSPC_ALUNOS" ENABLE STORAGE IN ROW CHUNK 8192
    NOCACHE LOGGING  NOCOMPRESS  KEEP_DUPLICATES ) ;
--------------------------------------------------------
--  DDL for Table T_CLIENTE
--------------------------------------------------------

CREATE TABLE "T_CLIENTE"
(	"ID_CLIENTE" NUMBER(*,0),
     "NOME_COMPLETO" VARCHAR2(255 BYTE),
     "DT_NASCIMENTO" DATE,
     "ENDERECO" VARCHAR2(255 BYTE),
     "CPF" VARCHAR2(11 BYTE),
     "TEL_CELULAR" VARCHAR2(15 BYTE),
     "TEL_RESIDENCIAL" VARCHAR2(15 BYTE),
     "EMAIL" VARCHAR2(255 BYTE)
) SEGMENT CREATION DEFERRED
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
    NOCOMPRESS LOGGING
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Table T_EMPRESA
--------------------------------------------------------

CREATE TABLE "T_EMPRESA"
(	"CNPJ" VARCHAR2(18 BYTE),
     "NM_EMPRESA" VARCHAR2(255 BYTE),
     "DS_ENDERECO" VARCHAR2(255 BYTE),
     "SEGMENTOS" VARCHAR2(255 BYTE),
     "NR_TELEFONE" VARCHAR2(15 BYTE),
     "ID_EMPRESA" NUMBER(*,0),
     "EMAIL" VARCHAR2(255 BYTE)
) SEGMENT CREATION DEFERRED
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
    NOCOMPRESS LOGGING
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Table T_SEGURO
--------------------------------------------------------

CREATE TABLE "T_SEGURO"
(	"ID_SEGURO" NUMBER(*,0),
     "DT_CONTRATACAO_SEGURO" DATE,
     "NM_EMPRESA" VARCHAR2(255 BYTE),
     "ID_CLIENTE" NUMBER(*,0),
     "ID_BICICLETA" NUMBER(*,0),
     "ID_EMPRESA" NUMBER(*,0)
) SEGMENT CREATION DEFERRED
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
    NOCOMPRESS LOGGING
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Table T_VISTORIA
--------------------------------------------------------

CREATE TABLE "T_VISTORIA"
(	"ID_VISTORIA" NUMBER(*,0),
     "FOTOS" BLOB,
     "ID_CLIENTE" NUMBER(*,0),
     "ID_BICICLETA" NUMBER(*,0),
     "ID_SEGURO" NUMBER(*,0)
) SEGMENT CREATION DEFERRED
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
    NOCOMPRESS LOGGING
    TABLESPACE "TBSPC_ALUNOS"
    LOB ("FOTOS") STORE AS SECUREFILE (
    TABLESPACE "TBSPC_ALUNOS" ENABLE STORAGE IN ROW CHUNK 8192
    NOCACHE LOGGING  NOCOMPRESS  KEEP_DUPLICATES ) ;
REM INSERTING into RM98060.T_BICICLETA
SET DEFINE OFF;
REM INSERTING into RM98060.T_CLIENTE
SET DEFINE OFF;
REM INSERTING into RM98060.T_EMPRESA
SET DEFINE OFF;
REM INSERTING into RM98060.T_SEGURO
SET DEFINE OFF;
REM INSERTING into RM98060.T_VISTORIA
SET DEFINE OFF;

--------------------------------------------------------
--  DDL for Index SYS_C00459283
--------------------------------------------------------

CREATE UNIQUE INDEX "SYS_C00459283" ON "T_BICICLETA" ("ID_BICICLETA")
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Index SYS_C00444948
--------------------------------------------------------

CREATE UNIQUE INDEX "SYS_C00444948" ON "T_CLIENTE" ("ID_CLIENTE")
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Index SYS_C00459684
--------------------------------------------------------

CREATE UNIQUE INDEX "SYS_C00459684" ON "T_EMPRESA" ("CNPJ")
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Index SYS_C00459261
--------------------------------------------------------

CREATE UNIQUE INDEX "SYS_C00459261" ON "T_SEGURO" ("ID_SEGURO")
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "TBSPC_ALUNOS" ;
--------------------------------------------------------
--  DDL for Index SYS_C00459725
--------------------------------------------------------

CREATE UNIQUE INDEX "SYS_C00459725" ON "T_VISTORIA" ("ID_VISTORIA")
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "TBSPC_ALUNOS" ;

--------------------------------------------------------
--  Constraints for Table T_BICICLETA
--------------------------------------------------------

ALTER TABLE "T_BICICLETA" ADD PRIMARY KEY ("ID_BICICLETA")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
        TABLESPACE "TBSPC_ALUNOS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table T_CLIENTE
--------------------------------------------------------

ALTER TABLE "T_CLIENTE" ADD PRIMARY KEY ("ID_CLIENTE")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
        TABLESPACE "TBSPC_ALUNOS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table T_EMPRESA
--------------------------------------------------------

ALTER TABLE "T_EMPRESA" ADD PRIMARY KEY ("CNPJ")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
        TABLESPACE "TBSPC_ALUNOS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table T_SEGURO
--------------------------------------------------------

ALTER TABLE "T_SEGURO" ADD PRIMARY KEY ("ID_SEGURO")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
        TABLESPACE "TBSPC_ALUNOS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table T_VISTORIA
--------------------------------------------------------

ALTER TABLE "T_VISTORIA" ADD PRIMARY KEY ("ID_VISTORIA")
    USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
        TABLESPACE "TBSPC_ALUNOS"  ENABLE;