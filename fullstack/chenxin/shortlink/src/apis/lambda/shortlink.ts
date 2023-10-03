import { useContext, useInject, useConfig } from "@midwayjs/hooks";
import { Context } from "@midwayjs/express";
import { ShortLinkUrlService } from "../service/shortlink/url";
import { useShortCode } from "../hooks/shortCode";
import qs from "query-string";

interface IUrlObject {
  shortUrl?: string | null | String[];
  url?: string;
  [propName: string]: any;
}

interface IApiReturnObject {
  message: string;
  data: IUrlObject | null;
  code: number;
}

/**
 * 生成短链
 * @param {string} url: 原始链接
 * @returns {IApiReturnObject} {short: 短链接, raw:原始链接 }
 */
export const set = async (params: IUrlObject): Promise<IApiReturnObject> => {
  const ctx = useContext<Context>();
  const { CODE: SYSTEM_CODE } = useConfig("SYSTEM");

  //@ts-ignore
  params = params || ctx.request.body || {};

  if (ctx.req.method.toUpperCase() !== "POST") {
    return {
      message: SYSTEM_CODE.INVAILD_REQ_METHOD[1],
      data: null,
      code: SYSTEM_CODE.INVAILD_REQ_METHOD[0],
    };
  }
  if (!params.url) {
    return {
      message: SYSTEM_CODE.INVAILD_PARAMS[1],
      data: null,
      code: SYSTEM_CODE.INVAILD_PARAMS[0],
    };
  }

  const { PREFIX } = useConfig("SHORTURL_SERVER");
  const urlService = await useInject(ShortLinkUrlService);
  const code = useShortCode(params?.url);

  const result = await urlService.save({
    url: params?.url,
    shortUrl: `${PREFIX}/${code}`,
  });

  console.log('result', result);
  return {
    message: SYSTEM_CODE.SUCCESS[1],
    data: result,
    code: SYSTEM_CODE.SUCCESS[0],
  };
};

/**
 *
 * @param {IUrlParams} params: URL查询对象
 * @returns {IApiReturnObject} {short: 短链接, raw:原始链接 }
 */
export const get = async (params: IUrlObject): Promise<IApiReturnObject> => {
  const ctx = useContext<Context>();
  const { CODE: SYSTEM_CODE } = useConfig("SYSTEM");

  params = params || qs.parse(ctx.req.url.replace(/^[^\?]+/, "")) || {};

  if (!params.url && !params.shortUrl) {
    return {
      message: SYSTEM_CODE.INVAILD_PARAMS[1],
      data: null,
      code: SYSTEM_CODE.INVAILD_PARAMS[0],
    };
  }

  const urlService = await useInject(ShortLinkUrlService);
  const result = await urlService.query(params);
  return {
    message: SYSTEM_CODE.SUCCESS[1],
    data: result,
    code: SYSTEM_CODE.SUCCESS[0],
  };
};
