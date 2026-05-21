let id = 0;
const idToLongUrl = new Map();
const longUrlToId = new Map();

export function getOrInsertFromLongUrl(longUrl: string): number {
    if (longUrlToId.has(longUrl)) {
        return longUrlToId.get(longUrl);
    } else {
        id++;
        idToLongUrl.set(id, longUrl);
        longUrlToId.set(longUrl, id);
        return id;
    }
}

export function getLongUrlFromId(id: number): string {
    if (hasId(id)) {
        return idToLongUrl.get(id);
    }
    return '';
}

export function hasId(id: number): boolean {
    return idToLongUrl.has(id);
}
