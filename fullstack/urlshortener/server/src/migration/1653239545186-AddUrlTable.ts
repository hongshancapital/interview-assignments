import {MigrationInterface, QueryRunner} from "typeorm";

export class AddUrlTable1653239545186 implements MigrationInterface {
    name = 'AddUrlTable1653239545186'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE "url" ("id" uuid NOT NULL DEFAULT uuid_generate_v4(), "code" text NOT NULL, "originalUrl" text NOT NULL, "createTime" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(), CONSTRAINT "UQ_1ec820b835ce9c4bc6cc8a01d98" UNIQUE ("code"), CONSTRAINT "PK_7421088122ee64b55556dfc3a91" PRIMARY KEY ("id"))`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`DROP TABLE "url"`);
    }

}
