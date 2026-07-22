import { PublicRes } from "./publicres"

export class ShortUrlRes extends PublicRes {

    public shortUrl: string|undefined;

    constructor(success: boolean, errorMsg: string, shortUrl?: string) {
        super(success, errorMsg);
        this.shortUrl = shortUrl
    }

    public buildJson(): any {
        return {"success":this.success, "code": this.errorMsg,  "shortUrl": this.shortUrl};
    }
}