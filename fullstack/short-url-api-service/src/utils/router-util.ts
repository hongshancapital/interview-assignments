import { Request, Response } from "express"

type Handler = (req: Request, res: Response, next: (error0: any) => void) => Promise<void>;

const requestHandler = (fn: (req0: Request, res0: Response) => void): Handler => {

    const handler: Handler = async (req: Request, res: Response, next: (error0: any) => any) => {
        try {
            fn(req, res)
        } catch (error) {
            next(error)
        }
    }

    return handler
}

export { requestHandler }

