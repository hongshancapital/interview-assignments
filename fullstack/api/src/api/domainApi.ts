import { Request, Response } from "express";
import { getDomainByUrl, getUrlByDomain } from "../service/domainService";

const SUCCESS = "success";
const FAIL = "fail";

function setSuccess(res: any, result: any) {
    res.json({ status: SUCCESS, results: result });
}

function setFail(res: any, message?: string, result?: any) {
    res.json({ status: FAIL, message, results: result });
}

export async function transferDomainToUrl(req: Request, res: Response) {
    try {
        const domain = req.body.domain;
        const url = await getUrlByDomain(domain);
        setSuccess(res, url);
    } catch (err) {
        console.error(err);
        setFail(res, "error");
    }
}

export async function transferUrlToDomain(req: Request, res: Response) {
    try {
        const url = req.params.url;
        const domain = await getDomainByUrl(url);
        setSuccess(res, domain);
    } catch (err) {
        console.error(err);
        setFail(res, "error");
    }
}