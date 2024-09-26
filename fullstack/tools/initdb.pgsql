DROP TABLE IF EXISTS links;
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
CREATE SEQUENCE IF NOT EXISTS links_id_seq;

-- Table Definition
CREATE TABLE links (
    "id" int4 NOT NULL DEFAULT nextval('links_id_seq'::regclass),
    "origin_url" varchar NOT NULL,
    "short_url" bpchar(8) NOT NULL,
    "created_at" int8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE UNIQUE INDEX links_pkey ON public.links USING btree (id);
CREATE UNIQUE INDEX links_origin_url_idx ON public.links USING btree (origin_url);
CREATE UNIQUE INDEX links_short_url_idx ON public.links USING btree (short_url);
ALTER TABLE links CLUSTER ON links_short_url_idx;