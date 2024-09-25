import type { Context } from '@midwayjs/koa';

import {
    Api,
    Get,
    Post,
    Validate,
    useContext,
    Query,
    useConfig,
} from '@midwayjs/hooks';
import { z } from 'zod';
import validUrl from 'valid-url';
import { prisma } from '../prisma';
import useShortSchema from "../hooks/useShortSchema";

/**
 * 生成短链：
 * 短域名存储接口：接受长域名信息，返回短域名信息， 并存下来
 * 短域名长度最大为 8 个字符
 */
export const createShortLink = Api(
    Post('/link/short'),
    Validate(
      z.string(),
      z.string().email(),
      z.string(),
    ),
    async (
      name: string,
      email: string,
      longLink: string,
    ) => {
        const ctx = useContext<Context>();
        if (!validUrl.isUri(longLink)) {
            ctx.throw(400, '请输入正确的链接地址');
            return;
        }
        const shortLink = useShortSchema();
        try {
           await prisma.user.create({
            data: {
                name,
                email,
                links: {
                create: [
                    {
                        longLink,
                        shortLink,
                    },
                ],
                }
            },
            });
            return shortLink;
        } catch (error) {
            ctx.throw(400, '转换失败');
            return [];
        }
    }
);

/**
 * 短域名读取接口：接受短域名信息，返回长域名信息。
 */
export const getLongLink = Api(
    Get('/link/long'),
    Query<{ shortLink: string }>(),
    async () => {

        const ctx = useContext<Context>();
        const { shortLink } = ctx.query;
        const links = await prisma.links.findMany({
            where: {
                shortLink,
            },
            include: { user: true },
        });
        return links;
    }
);


/**
 * 访问短域名，重定向至长域名
 */
export const getLongRedirect= Api(
    Get('/ppya.cn/t/:schema'), 
    // Query<{ shortUrl: string }>(),
    async () => {
        const ctx = useContext<Context>();
        const { schema } = ctx.params;
        const links = await prisma.links.findMany({
            where: {
                shortLink: `https://ppya.cn/t/${schema}`,
            },
            // include: { user: true },
        });
        if (links && links[0]) {
            // 重定向至原链接
            ctx.redirect(links[0].longLink);
        } else {
            ctx.throw(400, 'No url found');
        }
  });