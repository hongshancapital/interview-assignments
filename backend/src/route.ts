import { Router, Request, Response } from "express";
import {getOriginUrl, createShortUrl} from "./controller";
import {isValidHttpUrl} from "./utils"

export const router:Router = Router();

interface RequestBody {
    originUrl:string
}
interface RequestQuery {
    sid: string;
}

router.post('/l2s', async (req:Request<any, any, RequestBody, any>, res:Response)=>{
    const {body} = req
    const {originUrl} = body||{}
    if(originUrl && isValidHttpUrl(originUrl)){
        try{
            const createResult = await createShortUrl(originUrl);
            res.send(createResult)
        }catch(err){
            console.log('create error: ', err)
            res.sendStatus(500) 
        }
    }else{
        const failReason = 'no origin url or invalid url'
        res.statusMessage = failReason
        res.status(400);
        res.send(failReason)
    }
});
router.get('/s2l', async (req:Request<any, any, any, RequestQuery>, res:Response)=>{
    const {query} = req;
    const {sid} = query
    if(!sid){
        res.sendStatus(400)
        return;
    }
    try{
        const originUrlInfo = await getOriginUrl(sid);
        res.send(originUrlInfo)
    }catch(err){
        res.send(500)
    }
});

