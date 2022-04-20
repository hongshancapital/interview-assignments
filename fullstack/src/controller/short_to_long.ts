import {Request, Response} from "express";
import {RedisClient} from "../utils/redis_setup";
import {GetDbByShortDomain} from "../utils/postgres_setup";
import {BASE_URL, EXPIRE_TIME, GENERATE_LENGTH} from "../server";

export async function ShortToLong(req: Request, res: Response) {
    try {
        const reqQuery = req.query.query;
        if (!reqQuery || typeof  reqQuery !== "string") {
            throw 'BadRequest';
        }

        const reqObj = JSON.parse(reqQuery);
        if (!validateShortName(reqObj)) {
            return res.status(400).send({
                error: {
                    code: -3,
                    msg: "Wrong shortDomain!"
                }
            });
        }

        const shorter = reqObj.shortDomain.substring(BASE_URL.length);
        const longName = await RedisClient.get(shorter);
        if (!longName) {
            const stored = await GetDbByShortDomain(shorter);
            if (!stored) {
                return res.status(400).send({
                    error: {
                        code: -4,
                        msg: "Can't found stored long domain, please generate!"
                    }
                });
            }
            await RedisClient.set(shorter, stored.longDomain, {EX: EXPIRE_TIME});
            res.json(stored);
        } else {
            await RedisClient.expire(shorter, EXPIRE_TIME);
            res.json({
                shortDomain: BASE_URL + shorter,
                longDomain: longName,
            });
        }


    } catch (e) {
        return res.status(400).send({
            error: {
                code: -2,
                msg: "Bad Request!"
            }
        })
    }
}

function validateShortName(reqObj: any) {
    return !(!reqObj.shortDomain || reqObj.shortDomain.length !== GENERATE_LENGTH + BASE_URL.length);
}