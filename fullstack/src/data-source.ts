import "reflect-metadata"
import { DataSource } from "typeorm"
import { databaseConfig } from "./config"

export const AppDataSource = new DataSource(databaseConfig)
