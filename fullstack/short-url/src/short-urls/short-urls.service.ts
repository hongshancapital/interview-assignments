import { ShortUrl, ShortUrlCreate } from './short-url.interface';
import { decode, encode, HashId, NumberId } from '@/utils/short-id-converter';
import { Context } from '@/utils/context';
import { AppError, HttpCode } from '@/common/app-error';

export const find = async (shortId: HashId, ctx: Context): Promise<ShortUrl | undefined> => {
  if (shortId.length !== 8) {
    throw new Error('Length not match 8');
  }
  let urlId: NumberId;
  try {
    urlId = decode(shortId);
  } catch (e) {
    throw new AppError({
      httpCode: HttpCode.BAD_REQUEST,
      description: 'Invalid ShortId',
    });
  }
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
  let url: URL;
  try {
    url = new URL(newUrl.url);
  } catch (e) {
    throw new AppError({
      httpCode: HttpCode.BAD_REQUEST,
      description: 'Invalid URL',
    });
  }

  const createdUrl = await ctx.prisma.url.create({
    data: {
      url: url.toString(),
    },
  });

  const shortUrl = encode(createdUrl.id);

  return <ShortUrl>{
    id: createdUrl.id,
    shortId: shortUrl,
    url: createdUrl.url,
  };
};
