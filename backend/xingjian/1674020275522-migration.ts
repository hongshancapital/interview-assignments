import { Table, TableIndex, MigrationInterface, QueryRunner } from "typeorm"

/*
 * migration logic
 * 1.这里是创建表格的逻辑，为了执行这个操作需要修改datasource.ts中的数据库配置，并且假设你的数据库中有test这个database;
 * 2.安装typeorm相关的依赖，具体可以参考https://typeorm.io/migrations;
 * 3.如果成功运行，会创建一个test.short_url_info表格用户存储url信息;
 */

// 这里的table创建是通过typeorm工具来设置的，所以就不写相关的schema了，我把table创建语句贴在下面；
const create_table_schema = 'CREATE TABLE `short_url_info` (`id` int NOT NULL AUTO_INCREMENT, `short_url` varchar(8) NOT NULL, `long_url` varchar(256) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB';

const table_name:string = "short_url_info"

export class migration1674020275522 implements MigrationInterface {
    async up(query_runner: QueryRunner): Promise<void> {
        console.log("INFO: Database migration")
        // drop database if exists
        await query_runner.dropTable(table_name, true, true, true)

        // create table "short_url_info" and create index on column "short_url" and "long url"
        await query_runner.createTable(
            new Table({
                name: table_name,
                columns: [
                    {
                        name: "id",
                        type: "int",
                        isPrimary: true,
                        isGenerated: true,
                        generationStrategy: "increment"
                    },
                    {
                        name: "short_url",
                        type: "varchar",
                        length: "8",
                        isNullable: false
                    },
                    {
                        name: "long_url",
                        type: "varchar",
                        length: "256",
                        isNullable: false
                    }
                ]
            }),
            true
        )

        await query_runner.createIndex(
            table_name,
            new TableIndex({
                name: "short_url_index",
                columnNames: ["short_url"]
            })
        )

        await query_runner.createIndex(
            table_name,
            new TableIndex({
                name: "long_url_index",
                columnNames: ["long_url"]
            })
        )
    }
    async down(query_runner: QueryRunner): Promise<any> {
        // if create fail drop the table to rollback    
        await query_runner.dropTable(table_name, true, true, true)
    }
}