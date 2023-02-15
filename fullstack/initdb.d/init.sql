create sequence sl_id_seq increment by 1 minvalue 1 no maxvalue start with 1;

create table sl_data (
  id varchar(8) primary key not null,
  link varchar(8182) not null,
  t timestamp default current_timestamp
);