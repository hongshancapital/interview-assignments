import {Request, Response} from "express";
import {client} from "../utils/redis_setup";

export async function GetLongByShorter(req: Request, res: Response) {
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

        const longName = await client.get(reqObj.shortDomain)
        if (!longName) {
            return res.status(400).send({
                error: {
                    code: -4,
                    msg: "Request short name is invalid, Please generate again!"
                }
            });
        }


        res.json({
            shortDomain: reqObj.shortDomain,
            longDomain: longName
        });
    } catch (e) {
        return res.status(400).send({
            message: 'Bad Request!'
        })
    }


}

function validateShortName(reqObj: any) {
    return !(!reqObj.shortDomain || reqObj.shortDomain.length !== 8);
}