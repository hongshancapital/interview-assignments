import {Request, Response} from "express";
import {GetDbByLongDomain, SaveMapToDb} from "../utils/postgres_setup";
import assert from "assert";
import {BASE_URL} from "../server";
import {DispatchShort} from "../utils/redis_setup";
import {ShortLongMap} from "../types";

export async function LongToShort(req: Request, res: Response) {
    const longName = req.body.longDomain;
    if (!validateLongname(longName)) {
        return res.status(400).send({
            error: {
                code: -1,
                msg: "Wrong long name!"
            }
        });
    }

    let stored: ShortLongMap | undefined;

    stored = await GetDbByLongDomain(longName);
    if (!stored) {
        stored = await DispatchShort(longName);
        await SaveMapToDb(stored);
    }

    //是的，这里要是报错，那一定是上游的错误！
    assert(stored)
    res.json(stored);
}

function validateLongname(longName: string | undefined) {
    if (!longName) {
        return false;
    }

    return !!longName.match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g);
}