ALTER SESSION SET CONTAINER=@oracle.pdb@;
CREATE USER @oracle.roadmap.username@ IDENTIFIED BY @oracle.roadmap.password@;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW, CREATE SYNONYM TO @oracle.roadmap.username@;
GRANT UNLIMITED TABLESPACE TO @oracle.roadmap.username@;