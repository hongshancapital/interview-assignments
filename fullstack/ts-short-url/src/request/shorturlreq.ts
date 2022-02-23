import { ServerCode } from "../exception/errorcode"
import { ServerError } from "../exception/servererror"

export class ShortUrlReq {

    private originalUrl: string = "";

    constructor() {}

    public init(reqBody: any): boolean {
        let result:boolean = false;
        try {
            this.originalUrl = reqBody.originalUrl;                
            result = true;
        } catch (error) {
            throw new ServerError(error, ServerCode.SU_REQ_ARG_ERROR);
        }
        return result;
    }

    public getOriginalUrl(): string {
        return this.originalUrl;
    }
}