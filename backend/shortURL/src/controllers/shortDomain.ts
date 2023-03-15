import {Request, Response} from "express";
import {getDomain, createShortDomain} from "../service/domainExchange";


const shortDomain = {
    async getDomain(req: Request, res: Response) {
        try {
            //参数校验略
            let domain = await getDomain(req.params.shortDomain) as string;
            res.json({'domain': domain});
        } catch (err) {
            res.status(500);
        }

    },
    async createShortDomain(req: Request, res: Response) {
        try {
            //参数校验略
            let shortURL = await createShortDomain(req.body.domain) as string;
            res.json({'shortDomain': shortURL});
        } catch (err) {
            res.status(500);
        }
    }
}

export default shortDomain