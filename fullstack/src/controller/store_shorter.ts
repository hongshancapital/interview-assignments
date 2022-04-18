import {Request, Response} from "express";
import {GetLongNameFromRedis} from "../utils/redis_setup";

export async function StoreShorterByLong(req: Request, res: Response) {
    const longName = req.body.longDomain;
    if (!validateLongname(longName)) {
        return res.status(400).send({
            error: {
                code: -1,
                msg: "Wrong long name!"
            }
        });
    }

    const shorter = await  GetLongNameFromRedis(longName)
    res.json({
        shortDomain: shorter,
        longDomain: longName
    });
}

function validateLongname(longName: string | undefined) {
    if (!longName) {
        return false;
    }

    return !!longName.match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g);
}

