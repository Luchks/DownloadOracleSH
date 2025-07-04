CREATE SEQUENCE IF NOT EXISTS seq_ba_user    
  START WITH 1    
  INCREMENT BY 1    
  NO MINVALUE    
  NO MAXVALUE    
  CACHE 1;

CREATE TABLE IF NOT EXISTS ba_user (    
  ba_user_id bigint NOT NULL DEFAULT nextval('seq_ba_user'::regclass), 
  name character varying(100),
  login_user character varying(50),
  password_user character varying(20),
  is_active boolean NOT NULL,
  CONSTRAINT pk_ba_user PRIMARY KEY (ba_user_id)
);

COMMENT ON COLUMN ba_user.ba_user_id IS 'PK de la tabla';
COMMENT ON COLUMN ba_user.name IS 'Nombre del usuario';
COMMENT ON COLUMN ba_user.login_user IS 'Login del usuario'; 
COMMENT ON COLUMN ba_user.password_user IS 'Clave del usuario'; 
COMMENT ON COLUMN ba_user.is_active IS 'true si esta activo, false si esta inactivo';

--Usuario administrador
INSERT INTO public.ba_user ("name",login_user,password_user,is_active)
	VALUES ('Administrador','admin','admin',true);
