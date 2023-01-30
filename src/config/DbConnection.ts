import "reflect-metadata";
import { DataSource } from "typeorm";
import { DataNodeEntity } from "../entity/DataNodeEntity";
import { SlotOffsetDataEntity } from "../entity/SlotOffsetDataEntity";

const AppDataSource = new DataSource({
    "type": "mysql",
    "host": "localhost",
    "port": 3306,
    "username": "root",
    "password": "123456",
    "database": "mytest",
    "synchronize": false,
    "logging": false,
    "entities": [
      DataNodeEntity, SlotOffsetDataEntity
    ],
    "migrations": [
       "src/migration/**/*.ts"
    ],
    "subscribers": [
     "src/subscriber/**/*.ts"
    ]
})

export {AppDataSource};