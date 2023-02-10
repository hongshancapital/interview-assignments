import { InvalidUrlError } from '../errors'
import crc32 from 'crc-32'

export default class Url {
    readonly crc32: number;

    constructor(public url: string) {
        if (!Url.isUrlValid(url)) {
            throw new InvalidUrlError(`url 格式不正确:${url}`)
        }

        this.crc32 = crc32.str(url) >>> 0
    }

    private static isUrlValid(url: string): boolean {
        return /(((https?:(?:\/\/)?)(?:[\-;:&=\+\$,\w]+@)?[A-Za-z0-9\.\-]+|(?:www\.|[\-;:&=\+\$,\w]+@)[A-Za-z0-9\.\-]+)((?:\/[\+~%\/\.\w\-_]*)?\??(?:[\-\+=&;%@\.\w_]*)#?(?:[\.\!\/\\\w]*))?)/.test(url)
    }
}