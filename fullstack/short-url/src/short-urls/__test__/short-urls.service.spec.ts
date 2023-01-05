import { Context, createMockContext, MockContext } from '../../utils/context';
import * as shortUrlsService from '../short-urls.service';
import { decode, encode } from '../../utils/short-id-converter';

describe('short urls service', () => {
  let mockCtx: MockContext;
  let ctx: Context;

  beforeEach(() => {
    mockCtx = createMockContext();
    ctx = mockCtx as unknown as Context;
  });

  it('should return ShortUrl when create from url', async () => {
    const newUrl = {
      url: 'https://google.com',
    };
    const urlItem = {
      id: 3,
      url: 'https://google.com',
      createdAt: new Date(),
    };
    mockCtx.prisma.url.create.mockResolvedValue(urlItem);

    const shortUrl = await shortUrlsService.create(newUrl, ctx);

    expect(mockCtx.prisma.url.create).toBeCalled();
    expect(mockCtx.prisma.url.create).toHaveBeenCalledWith({ data: { url: 'https://google.com' } });
    expect(shortUrl.url).toEqual(newUrl.url);
    expect(shortUrl.id).toEqual(urlItem.id);
    expect(decode(shortUrl.shortId)).toEqual(shortUrl.id);
  });

  it('should return ShortUrl when find by shortId(HashId)', async () => {
    const urlNumberId = 3;
    const urlShortId = encode(urlNumberId);
    const urlItem = {
      id: urlNumberId,
      url: 'https://google.com',
      createdAt: new Date(),
    };
    mockCtx.prisma.url.findUnique.mockResolvedValue(urlItem);

    const shortUrl = await shortUrlsService.find(urlShortId, ctx);

    expect(mockCtx.prisma.url.findUnique).toBeCalled();
    expect(mockCtx.prisma.url.findUnique).toHaveBeenCalledWith({ where: { id: urlNumberId } });
    expect(shortUrl).toHaveProperty('url', urlItem.url);
    expect(shortUrl).toHaveProperty('id', urlNumberId);
  });

  it('should throw length not match error when find with not 8 length shortId(HashId)', async () => {
    expect(shortUrlsService.find('1234567', ctx)).rejects.toThrowError('Length not match 8');
    expect(shortUrlsService.find('123456789', ctx)).rejects.toThrowError('Length not match 8');
  });

  it('should return undefined when find with shortId(HashId) is not exists', async () => {
    mockCtx.prisma.url.findUnique.mockResolvedValue(null);
    const shortUrl = await shortUrlsService.find(encode(100), ctx);
    expect(shortUrl).toBe(undefined);
  });
});
