import { Request, RequestHandler } from 'express';
import Joi from 'joi';
import requestMiddleware from '../../middleware/request-middleware';
import Link from '../../models/Link';
import { nanoid } from 'nanoid';
import { AppSettings } from '../../settings';
import logger from '../../logger';

export const shortenLinkSchema = Joi.object()
  .keys({
    url: Joi.string()
      .uri({
        scheme: [
          'http',
          'https'
        ]
      })
      .required()
  });

interface ShortenReqBody {
  url: string;
}

export const generateKeyword: () => Promise<string> = async () => {
  const keyword = nanoid(AppSettings.MAX_KEYWORD);
  const linkByKeyword = await Link.findOne({
    keyword
  });
  if (linkByKeyword) {
    logger.silly(`keyword conflicts: ${keyword}, loop for another`);
    return await generateKeyword();
  }
  logger.silly(`success generated keyword: ${keyword}`);
  return keyword;
};

/** Should synchronize this method when considering the high concurrency condition */
const shorten: RequestHandler = async (req: Request<{}, {}, ShortenReqBody>, res) => {
  const { url } = req.body;

  const link = await Link.findOne({
    url: url.trim()
  });
  if (link) {
    res.status(200)
      .send({
        keyword: link.keyword
      });
    return;
  }

  const newKeyword: string = await generateKeyword();

  const newLink = new Link({
    keyword: newKeyword,
    url
  });
  await newLink.save();

  res.status(200)
    .send({
      keyword: newLink.keyword
    });
};

export default requestMiddleware(shorten, { validation: { body: shortenLinkSchema } });
