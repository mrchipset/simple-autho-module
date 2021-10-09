drop table if exists  public.tb_user;
drop sequence if exists public.user_id_seq;
CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    minvalue 0
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_id_seq OWNER TO postgres;

create table if not exists public.tb_user (
    user_id integer DEFAULT nextval('public.user_id_seq') NOT NULL,
    username character varying(128) NOT NULL,
    email character varying(128) NOT NULL,
    create_date date DEFAULT ('now'::text)::date NOT NULL,
    last_login timestamp without time zone DEFAULT now(),
    passwd character(32) NOT NULL
);

create index idx_username ON public.tb_user USING btree(username);
ALTER TABLE public.tb_user ADD CONSTRAINT constraint_unique_column UNIQUE (username, email);