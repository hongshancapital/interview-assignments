import { AppSettings } from '../../settings';
import { Request, RequestHandler } from 'express';
import Link from '../../models/Link';
import logger from '../../logger';
import requestMiddleware from '../../middleware/request-middleware';

const resume: RequestHandler = async (req: Request, res) => {
  const { keyword } = req.params;
  logger.silly(`Link to get: ${keyword}`);
  if (!keyword || keyword.trim().length > AppSettings.MAX_KEYWORD) {
    return res.status(400)
      .send({
        error: 'invalid link keyword'
      });
  }

  const link = await Link.findOne({
    keyword: keyword.trim()
  });
  if (!link) {
    return res.status(404)
      .send({
        error: 'Link not found'
      });
  }

  res.status(200)
    .send({
      url: link.url
    });
};

export default requestMiddleware(resume);
