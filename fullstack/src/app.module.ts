import { Module } from "@nestjs/common";
import { ShortLinkModule } from "./short-link/short-link.module";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ShortLink } from "./short-link/short-link.entity";
import { RedisModule } from "nestjs-redis";
import { Track } from "./short-link/track.entity";

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: "postgres",
      host: "localhost",
      port: 5432,
      username: "postgres",
      password: "123456",
      database: "postgres",
      entities: [ShortLink, Track],
      synchronize: true,
      logging: false
    }),
    RedisModule.register({
      url: "redis://127.0.0.1:6379/0"
    }),
    ShortLinkModule],
  controllers: [],
  providers: []
})
export class AppModule {
}
