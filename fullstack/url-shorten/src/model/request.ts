import { Request } from "express"

export default interface RequestWithBody extends Request {
    body: {
        [key: string]: string
    }
}
