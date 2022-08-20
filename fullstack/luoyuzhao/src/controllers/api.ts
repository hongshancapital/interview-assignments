import { Response, Request } from "express";
import { Container } from "typedi";
import ApiService from "../service/ApiService";


export const getApi = (req: Request, res: Response) => {
    res.send(
        "<h1>this is a test server for domain-convert here are api urls</h1>" +
        '<p>post: /api/long2short</p>' +
        '<p>post: /api/short2long</p>'
    );
};

const reg = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)/;
const service = Container.get(ApiService);
export const long2short = (req: Request, res: Response) => {
    try {
        let host = req.get('host');
        let url = req.body;
        let arr = url.split('/');
        if (reg.test(url) && arr.length > 3) {
            let domain = arr[2];
            let suffixUrl = url.substring(url.lastIndexOf(domain) + 1 + domain.length);
            let prefixUrl = url.replace(suffixUrl, '');
            service.long2short(suffixUrl).then(shortUrl => {
                res.send(prefixUrl + shortUrl);
            })
        }
        else {
            res.send('your request is not a valid url');
        }

    }
    catch (e) {
        res.send('500')
    }
};
export const short2long = (req: Request, res: Response) => {
    try {
        let url = req.body;
        let arr = url.split('/');
        if (reg.test(url) && arr.length == 4) {
            let domain = arr[2];
            let suffixUrl = arr[3];
            let prefixUrl = url.replace(suffixUrl, '');
            service.short2long(suffixUrl).then(longUrl => {
                res.send(prefixUrl + longUrl);
            })
        }
        else {
            res.send('your request is not a valid short url');
        }
    }
    catch (e) {
        res.send('500')
    }
};