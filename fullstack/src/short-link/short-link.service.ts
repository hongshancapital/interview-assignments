import { Inject, Injectable } from "@nestjs/common";
import { Repository } from "typeorm";
import { ShortLink } from "./short-link.entity";
import { InjectRepository } from "@nestjs/typeorm";
import { nanoid } from "nanoid";
import * as dayjs from "dayjs";
import { Track } from "./track.entity";

@Injectable()
export class ShortLinkService {

  constructor(
    @InjectRepository(ShortLink)
    private readonly shortLinkRepository: Repository<ShortLink>,
    @InjectRepository(Track)
    private readonly trackRepository: Repository<Track>
  ) {
  }

  async generateShortLink(sourceLink: string) {
    let validList = await this.validShortLinks();
    while (true) {
      let linkStr = nanoid(8);
      if (!validList.includes(linkStr)) {
        let link = new ShortLink();
        link.sourceLink = sourceLink;
        link.generateLink = linkStr;
        link.expired = dayjs().add(10, "day").toDate();
        await this.saveShortLink(link);
        return linkStr;
      }
    }
  }

  saveShortLink(link: ShortLink) {
    this.shortLinkRepository.insert(link);
  }

  async validShortLinks() {
    let list = await this.shortLinkRepository.createQueryBuilder("l")
      .select(["l.generateLink"])
      .getMany();
    return list.map(l => l.generateLink);
  }

  async findShortLink(short: string) {
    let link = await this.shortLinkRepository.findOne({ generateLink: short });
    return link;
  }

  saveTrack(link: ShortLink, headers: any, ip: string,uuid:string) {
    this.trackRepository.insert({
      shortLinkId: link.id,
      userAgent: headers["user-agent"],
      referer: headers["referer"],
      language: headers["accept-language"],
      uuid,
      ip
    });
  }
}