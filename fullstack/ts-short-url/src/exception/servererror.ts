
import {ServerCode} from "./errorcode"

export class ServerError extends Error {
    
    public code: ServerCode;

    constructor(msg: any, code: ServerCode) {
        super(msg)
        this.code = code;
    }
}
   