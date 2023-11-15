/*
PostgreSQL Backup
Database: pakgo/public
Backup Time: 2023-10-26 20:55:07
*/

DROP SEQUENCE IF EXISTS "public"."pakgo_shipments_id_seq";
DROP TABLE IF EXISTS "public"."pakgo_shipments";
CREATE SEQUENCE "pakgo_shipments_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;
CREATE TABLE "pakgo_shipments" (
  "id" int4 NOT NULL DEFAULT nextval('pakgo_shipments_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "phone_number" varchar(15) COLLATE "pg_catalog"."default",
  "item_type" varchar(255) COLLATE "pg_catalog"."default",
  "weight" numeric,
  "cost" numeric,
  "source_city" varchar(255) COLLATE "pg_catalog"."default",
  "destination_city" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "pakgo_shipments" OWNER TO "postgres";
BEGIN;
LOCK TABLE "public"."pakgo_shipments" IN SHARE MODE;
DELETE FROM "public"."pakgo_shipments";
INSERT INTO "public"."pakgo_shipments" ("id","name","address","phone_number","item_type","weight","cost","source_city","destination_city") VALUES (8, 'kennesty', 'surabaya', '0987654358', 'tas sekolah', 6, 800000, 'surabaya', 'jakarta'),(9, 'choir', 'jakarta', '086876543', 'topi', 9, 123000, 'surabaya', 'lamongan'),(10, 'intan', 'bekasi', '0765542', 'kerudung', 4, 60000, 'surabaya', 'jakarta')
;
COMMIT;
ALTER TABLE "pakgo_shipments" ADD CONSTRAINT "pakgo_shipments_pkey" PRIMARY KEY ("id");
ALTER SEQUENCE "pakgo_shipments_id_seq"
OWNED BY "pakgo_shipments"."id";
SELECT setval('"pakgo_shipments_id_seq"', 42, true);
ALTER SEQUENCE "pakgo_shipments_id_seq" OWNER TO "postgres";
