import { Body, Controller, Get, Inject, Param, Post, Req, Res } from "@nestjs/common";
import { ApiParam, ApiTags } from "@nestjs/swagger";
import { ShortLinkService } from "./short-link.service";
import { GenerateShortLinkDto } from "./dto/generateShortLinkDto";
import { RedisService } from "nestjs-redis";
import { MatchLinkService } from "./match-link.service";
import { Response, Request } from "express";

@ApiTags("sl")
@Controller()
export class ShortLinkController {

  @Inject()
  matchLinkService: MatchLinkService;


  constructor(
    private readonly shortLinkService: ShortLinkService
  ) {
  }

  @Post("/generateShortLink")
  async generateShortLink(@Body() body: GenerateShortLinkDto) {
    let url = await this.shortLinkService.generateShortLink(body.url);
    return { code: 200, data: url };
  }

  @Get(":short")
  @ApiParam({ name: "short" })
  async matchShortLink(@Param("short") short: string, @Res() res?: Response, @Req() req?: Request) {
    return this.matchLinkService.matchShortLink(short, res, req);
  }

}
