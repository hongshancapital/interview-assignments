import { Module } from "@nestjs/common";
import { ShortLinkController } from "./short-link.controller";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ShortLink } from "./short-link.entity";
import { ShortLinkService } from "./short-link.service";
import { Track } from "./track.entity";
import { MatchLinkService } from "./match-link.service";

@Module({
  imports: [
    TypeOrmModule.forFeature([ShortLink, Track])
  ],
  providers: [ShortLinkService, MatchLinkService],
  controllers: [ShortLinkController]
})
export class ShortLinkModule {
}
