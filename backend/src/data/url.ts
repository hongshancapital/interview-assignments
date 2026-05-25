import { ErrorUrlCreatorTooLong, ErrorUrlDescriptionTooLong, ErrorUrlOriginNotFound, ErrorUrlOriginUrlTooLong, ErrorUrlRequiredCreator, ErrorUrlRequiredDescription, ErrorUrlRequiredOriginUrl } from "../biz/errors/url";
import UrlModel, { UrlStatusOpen, creatorMaxLength, descriptionMaxLength, originUrlMaxLength } from "./models/url"
import { newId } from "./id";
import memoryCache from "./urlcache"


export interface NewUrlRequest {
    originUrl: string,
    description: string,
    creator: string,
    expires?: Date,
}

// 创建一个新的短链
export const newUrl = async ({ originUrl, description, creator, expires }: NewUrlRequest) => {
    if (!originUrl) {
        throw ErrorUrlRequiredOriginUrl;
    }
    if (originUrl.length > originUrlMaxLength) {
        throw ErrorUrlOriginUrlTooLong;
    }
    if (!description) {
        throw ErrorUrlRequiredDescription;
    }
    if (description.length > descriptionMaxLength) {
        throw ErrorUrlDescriptionTooLong;
    }
    if (!creator) {
        throw ErrorUrlRequiredCreator;
    }
    if (creator.length > creatorMaxLength) {
        throw ErrorUrlCreatorTooLong;
    }

    const shortenUrl = await newId();

    return await UrlModel.create({
        shortenUrl: shortenUrl,
        originUrl: originUrl,
        description: description,
        creator: creator,
        expires: expires,
    });
}

export const memoryCacheMissing = '@@@@missing';
export const memoryCacheMissingTTL = 1000

// 获取短链对应的原始链接 - 缓存
export const getOriginUrlFromCache = async (shortUrl: string) => {
    const cache = await memoryCache;

    let data: string | undefined = await cache.get<string>(shortUrl)

    if (!data) {
        // logger.info(`cache missed: ${shortUrl}`)
        const urlData = await UrlModel.findOne({ shortenUrl: shortUrl }).select(["originUrl", "status", "expire"])
        if (!urlData) {
            // 数据库中没有，设置missing保护，缓存时长较短
            cache.set(shortUrl, memoryCacheMissing, memoryCacheMissingTTL)
        } else if (UrlStatusOpen !== urlData.status || urlData.expires && urlData.expires < new Date()) {
            // 数据库中有，但是已经失效，设置missing保护，缓存时长与正常缓存相同
            urlData.originUrl = memoryCacheMissing
            cache.set(shortUrl, memoryCacheMissing);
        } else {
            // 数据库中有，设置缓存
            cache.set(shortUrl, urlData.originUrl)
        }

        data = urlData?.originUrl ?? memoryCacheMissing;
    }

    if (memoryCacheMissing === data) {
        throw ErrorUrlOriginNotFound;
    } else {
        return data;
    }
}