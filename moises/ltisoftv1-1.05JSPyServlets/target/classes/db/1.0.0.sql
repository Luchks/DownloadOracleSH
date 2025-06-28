CREATE SEQUENCE IF NOT EXISTS seq_tb_product    
  START WITH 1    
  INCREMENT BY 1    
  NO MINVALUE    
  NO MAXVALUE    
  CACHE 1;

CREATE TABLE IF NOT EXISTS tb_product (    
  tb_product_id bigint NOT NULL DEFAULT nextval('seq_tb_product'::regclass),  
  code character varying(13),    
  name character varying(100) NOT NULL,    
  sales_price numeric(20,6),    
  CONSTRAINT pk_tb_product PRIMARY KEY (tb_product_id)
);

COMMENT ON COLUMN tb_product.tb_product_id IS 'PK de la tabla';
COMMENT ON COLUMN tb_product.code IS 'Codigo del producto';
COMMENT ON COLUMN tb_product.name IS 'Nombre';
COMMENT ON COLUMN tb_product.sales_price IS 'Precio'; 


CREATE SEQUENCE IF NOT EXISTS seq_tb_product_type    
  START WITH 1    
  INCREMENT BY 1    
  NO MINVALUE    
  NO MAXVALUE    
  CACHE 1;

CREATE TABLE IF NOT EXISTS tb_product_type (    
  tb_product_type_id bigint NOT NULL DEFAULT nextval('seq_tb_product_type'::regclass),  
  code character varying(13),    
  name character varying(100) NOT NULL,    
  CONSTRAINT pk_tb_product_type PRIMARY KEY (tb_product_type_id)
);

COMMENT ON COLUMN tb_product_type.tb_product_type_id IS 'PK de la tabla';
COMMENT ON COLUMN tb_product_type.code IS 'Codigo del tipo de producto';
COMMENT ON COLUMN tb_product_type.name IS 'Nombre';

ALTER TABLE public.tb_product ADD tb_product_type_id bigint NULL;
ALTER TABLE public.tb_product ADD CONSTRAINT tb_product_tb_product_type_fk FOREIGN KEY (tb_product_type_id) REFERENCES public.tb_product_type(tb_product_type_id);

