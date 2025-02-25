import ShortUrl, { IShortUrl } from 'schemas/ShortUrl';
import nanoid from 'utils/nanoid';

async function create(longUrl: string, length = 8): Promise<IShortUrl> {
  const doc = await ShortUrl.findOne({ longUrl });

  // don't create duplicate docs with same longUrl
  if (doc) {
    return doc;
  }

  // handle collisions
  let shortUrl = '';
  for (;;) {
    shortUrl = nanoid(length);

    // already exist, continue to generate a new one
    /* istanbul ignore else */
    if (!(await ShortUrl.findOne({ shortUrl }))) {
      break;
    }
  }

  return ShortUrl.create({ shortUrl, longUrl });
}

async function findByShortUrl(shortUrl: string): Promise<IShortUrl> {
  return ShortUrl.findOne({
    shortUrl,
  });
}

export default {
  create,
  findByShortUrl,
};
