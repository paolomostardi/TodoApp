-- crea il database per i ticket 

CREATE DATABASE tickets
    WITH
    OWNER = packt
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c tickets

-- Crea la tabella per i ticket 
-- PK è un id incrementale 
-- 

CREATE TABLE IF NOT EXISTS ticket
(
    id SERIAL PRIMARY KEY,
    proprietario character varying(255),
    descrizione character varying(500),
    stato  character varying(255),
    priorita integer,
    data_inizio date,
    data_fine date,
    data_deleted date
)

