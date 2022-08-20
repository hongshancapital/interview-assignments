import { hashUrl } from './hash';
import { storeInstance } from './store';

function createShortUrl(url: string, du = 0): string {
    if (!isValidHttpUrl(url)) {
        throw new Error('invalid url');
    }
    if (url.length > 255) {
        throw new Error('url too long');
    }
    let hash = hashUrl(url);
    let exists = storeInstance.has(hash);
    if (!exists) {
        let saveUrl = removeSuffix(url)
        storeInstance.save(hash, saveUrl);
    } else {
        //TODO: has为真存在误判的可能性
        let saved_url = storeInstance.load(hash);
        if (removeSuffix(saved_url) === removeSuffix(url)) {
            return hash
        }
        du++;
        return createShortUrl(url + '-DU' + du + '-')
    }
    return hash
}

function loadShortUrl(url: string): string {
    let exists = storeInstance.has(url);
    if (!exists) {
        throw new Error('url not found');
    }
    let long = storeInstance.load(url);
    if (!long) {
        throw new Error('url not found');
    }
    return long
}

function isValidHttpUrl(str: string): boolean {
    let url;
    try {
        url = new URL(str);
    } catch (_) {
        return false;
    }

    return url.protocol === "http:" || url.protocol === "https:";
}

function removeSuffix(url: string) {
    return url.replace(/\-DU\d+\-/g, '')
}

export {
    createShortUrl,
    loadShortUrl
}