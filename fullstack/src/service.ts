import { getShortUriEntity, saveShortUriEntity } from "@/data";
import { ShortUriEntity } from "@/data/entities/ShortUriEntity";
import { StringEncoder } from "@/util";

export async function getShortId(url: string) {
    const encoder = new StringEncoder(url);

    const shortUrlEntity = (await getShortUriEntity({
        urlHash: encoder.hash
    }).catch(() => null))
    if (shortUrlEntity) {
        return shortUrlEntity.shortId
    } else {
        const shortUriEntity = new ShortUriEntity();
        shortUriEntity.urlHash = encoder.hash;
        shortUriEntity.url = url;
        for (let retry = 0; retry < 3; retry++) {
            shortUriEntity.shortId = encoder.generateId();
            try {
                await saveShortUriEntity(shortUriEntity)
            } catch (err) {
                continue
            }

        }
        return shortUriEntity.shortId;
    }

}

export async function getUrlByShortId(shortId: string) {
    try {
        const url = (await getShortUriEntity({ shortId }))?.url;
        return url || null
    } catch (err) {
        return null
    }
}