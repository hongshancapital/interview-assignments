import { PublicRes } from "./publicres"

export class ShortUrlRes extends PublicRes {

    public shortUrl: string;

    constructor(success: boolean, errorMsg: string, shortUrl: string) {
        super(success, errorMsg);
        this.shortUrl = shortUrl
    }

    public buildJson(): any {
        return {"isSucc":this.success, "errorMsg": this.errorMsg,  "shortUrl": this.shortUrl};
    }
}