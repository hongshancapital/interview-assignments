import { ServerCode } from "../exception/errorcode"
import { ServerError } from "../exception/servererror"

export class OriginalUrlReq {

    private shortUrl: string = "";

    constructor() {}

    public init(reqBody: any): boolean {
        let result:boolean = false;
        try {
            this.shortUrl = reqBody.shortUrl;                
            result = true;
        } catch (error) {
            throw new ServerError(error, ServerCode.SU_REQ_ARG_ERROR);
        }
        return result;
    }
    public getShortUrl(): string {
        return this.shortUrl;
    }
}