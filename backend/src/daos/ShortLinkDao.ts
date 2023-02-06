import ShortLink, { ShortLinkInput, ShortLinkOuput } from "../models/ShortLink";

async function createShortLink(payload: ShortLinkInput): Promise<ShortLinkOuput> {
  return ShortLink.create(payload);
}

async function findByShortUrl(shortUrl: string): Promise<ShortLinkOuput | null> {
  return ShortLink.findOne({
    where: {shortUrl}
  });
}

async function findByOriginalUrl(originalUrl: string): Promise<ShortLinkOuput | null> {
  return ShortLink.findOne({
    where: {originalUrl}
  });
}

export default {
  createShortLink, findByShortUrl, findByOriginalUrl
}