import { PublicRes } from "./publicres"

export class OriginalurlRes extends PublicRes {

    public originalUrl: string;

    constructor(success: boolean, errorMsg: string, originalUrl: string) {
        super(success, errorMsg);
        this.originalUrl = originalUrl
    }

    public buildJson(): any {
        return {"success":this.success, "errorMsg": this.errorMsg,  "originalUrl": this.originalUrl};
    }
}