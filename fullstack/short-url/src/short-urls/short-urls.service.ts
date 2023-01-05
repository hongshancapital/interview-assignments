import { ShortUrl, ShortUrlCreate } from './short-url.interface';
import { decode, encode, HashId } from '../utils/short-id-converter';
import { Context } from '../utils/context';

export const find = async (shortId: HashId, ctx: Context): Promise<ShortUrl | undefined> => {
  if (shortId.length !== 8) {
    throw new Error('Length not match 8');
  }
  const urlId = decode(shortId);
  const foundUrl = await ctx.prisma.url.findUnique({
    where: {
      id: urlId,
    },
  });
  if (!foundUrl) {
    return undefined;
  }

  return <ShortUrl>{
    id: foundUrl.id,
    shortId: shortId,
    url: foundUrl.url,
  };
};

export const create = async (newUrl: ShortUrlCreate, ctx: Context): Promise<ShortUrl> => {
  const createdUrl = await ctx.prisma.url.create({
    data: newUrl,
  });

  const shortUrl = encode(createdUrl.id);

  return <ShortUrl>{
    id: createdUrl.id,
    shortId: shortUrl,
    url: createdUrl.url,
  };
};
