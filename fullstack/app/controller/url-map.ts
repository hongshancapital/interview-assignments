import { Context, Controller } from 'egg';
import { IOriginalUrl, IParamTinyUrl, IRedirectUrl, ITinyUrl } from 'interface';
import { redirect, response, validate } from '../utils/decorator';
import { ParamValidateError } from '../utils/error';
import UrlMapService from '../service/url-map';
import BloomFilterService from '../service/bloom-filter';
import { getDate } from '../utils';
import { URL_PTEFIX } from '../utils/constants';

const URL_PARAMS = {
  type: 'string',
  required: true,
  allowEmpty: false,
  format: /^[0-9a-zA-Z]{8}$/,
};
export default class UrlMapController extends Controller {

  urlMapService: UrlMapService;
  bloomFilter: BloomFilterService;

  constructor(ctx: Context) {
    super(ctx);
    this.urlMapService = this.service.urlMap;
    this.bloomFilter = this.service.bloomFilter;
  }

  @redirect
  @validate('params', { tinyUrl: URL_PARAMS })
  async redirectOriginalUrl({ params }: { params: ITinyUrl }): Promise<IRedirectUrl> {
    const originalUrl = await this.urlMapService.getOriginalUrl(params.tinyUrl);
    if (!originalUrl) {
      throw new ParamValidateError('短链无效');
    }
    return { url: originalUrl };
  }

  @response
  @validate('query', { tinyUrl: URL_PARAMS })
  async getOriginalUrl({ params }: { params: ITinyUrl }): Promise<IOriginalUrl> {
    const originalUrl = await this.urlMapService.getOriginalUrl(params.tinyUrl);
    if (!originalUrl) {
      throw new ParamValidateError('短链无效');
    }
    return { originalUrl };
  }

  @response
  @validate('body', { tinyUrl: URL_PARAMS })
  async delUrl({ params }: { params: ITinyUrl }): Promise<{ count: number }> {
    const count = await this.urlMapService.delByTinyUrl(params.tinyUrl);
    return { count };
  }

  @response
  @validate('body', {
    originalUrl: 'string',
    creator: 'string',
    expire: 'int?',
  })
  async setInfo({ params }: { params: IParamTinyUrl }): Promise<ITinyUrl> {
    const bloomFlag = this.service.bloomFilter.has(params.originalUrl);
    if (bloomFlag) {
      throw new ParamValidateError('url已存在');
    }
    const tinyUrl = await this.urlMapService.generatorTinyUrl();
    const date = getDate(params.expire);

    await this.urlMapService.setInfo({
      tinyUrl,
      originalUrl: params.originalUrl,
      creator: params.creator,
      createDate: date.createDate,
      expireDate: date.expireDate,
    });
    return { tinyUrl: URL_PTEFIX + tinyUrl };
  }
}
