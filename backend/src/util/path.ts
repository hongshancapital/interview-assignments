export const joinUrl = (https: boolean, host: string, shortUrl: string) => {
    return `${https ? "https://" : "http://"}${host}/url/${shortUrl}`
}