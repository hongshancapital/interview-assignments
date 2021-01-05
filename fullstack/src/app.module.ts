import { Module } from "@nestjs/common";
import { AppController } from "./app.controller";
import { AppService } from "./app.service";
import { TypeOrmModule } from "@nestjs/typeorm";
import { Shorten } from "./entity/shorten";
import { TrackLink } from "./entity/tracklink";
import { Connection } from "typeorm";
import { ConfigModule } from "@nestjs/config";

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: "mongodb",
      host:
        process.env.NODE_ENV == "development" || process.env.NODE_ENV == "test"
          ? "localhost"
          : "host.docker.internal",
      port: 27017,
      database: "shortener",
      entities: [Shorten, TrackLink],
      synchronize: true,
      useUnifiedTopology: true,
      autoLoadEntities: true,
    }),
    ConfigModule.forRoot(),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
  constructor(private connection: Connection) {}
}
