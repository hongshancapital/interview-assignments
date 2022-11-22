import Hashids from 'hashids';

const salt: string | undefined = process.env.SALT;
const urllength: number = process.env.URL_LENGTH ? process.env.URL_LENGTH as unknown as number : 8182;

const hashids: Hashids = new Hashids(salt, 5)

const encodeID = (id: number): string => {
    return hashids.encode(id)
}

const decodeID = (hash: string): number => {
    return hashids.decode(hash)[0] as number;
}

const isURL = (str: string): boolean => {
    let urlRegex = '^(?:(?:http|https)://)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$';
    let url = new RegExp(urlRegex, 'i');
    return str.length < urllength && url.test(str);
}

const splitURL = (str: string): string[] => {
    let url = new URL(str)
    return [url.origin, url.pathname + url.search + url.hash]
}

const formatURL = (url: string): string => {
    return splitURL(url).join('')
}


function delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export { encodeID, decodeID, isURL, splitURL, delay, formatURL }
