import { Inject, Injectable } from "@nestjs/common";
import { ChainAction, StepChain } from "../common/StepChain";
import { Response, Request } from "express";
import { v4 as uuidv4 } from "uuid";
import { RedisService } from "nestjs-redis";
import { ShortLink } from "./short-link.entity";
import { ShortLinkService } from "./short-link.service";
import * as dayjs from "dayjs";
import * as qs from "querystring";

interface ShortLinkContext {
  short: string
  res: Response,
  req: Request
  shortLink?: ShortLink
  uuid?: string
}

@Injectable()
export class MatchLinkService {

  constructor(
    private readonly  redisService: RedisService,
    private readonly  shortLinkService: ShortLinkService
  ) {
  }

  private stepChain: StepChain<ShortLinkContext>;

  onModuleInit() {
    this.stepChain = new StepChain<ShortLinkContext>();
    this.stepChain.add("cookie", (c, a) => this.cookieStep(c, a));
    this.stepChain.add("readRecord", (c, a) => this.readRecordStep(c, a));
    this.stepChain.add("track", (c, a) => this.trackStep(c, a));
  }

  async matchShortLink(short: string, res: Response, req: Request) {
    let context: ShortLinkContext = {
      short, res, req
    };
    await this.stepChain.handle(context);
    res.redirect(302, context.shortLink.sourceLink);
  }

  private cookieStep(ctx: ShortLinkContext, action: ChainAction) {
    if (ctx.req.headers.cookie) {
      let parse = qs.parse(ctx.req.headers.cookie);
      if (parse.uuid) {
        // @ts-ignore
        ctx.uuid = parse.uuid;
      }
      return;
    }
    let uuid = uuidv4();
    ctx.res.cookie("uuid", uuid);
    ctx.uuid = uuid;
  }

  private async readRecordStep(ctx: ShortLinkContext, action: ChainAction) {
    let client = this.redisService.getClient();
    let redisRes = await client.get(ctx.short);
    if (redisRes) {
      try {
        let link: ShortLink = JSON.parse(redisRes);
        ctx.shortLink = link;
      } catch (e) {
        console.log(e);
        throw Error("parse error");
      }
    } else {
      let link = await this.shortLinkService.findShortLink(ctx.short);
      if (!link) {
        throw Error("not find");
      }
      ctx.shortLink = link;
      let sesond = dayjs(link.expired).diff(dayjs(), "second");
      client.setex(ctx.short, sesond > 600 ? 600 : sesond, JSON.stringify(link));
    }
  }

  private trackStep(ctx: ShortLinkContext, action: ChainAction) {
    this.shortLinkService.saveTrack(ctx.shortLink, ctx.req.headers, ctx.req.ip, ctx.uuid);
  }


}