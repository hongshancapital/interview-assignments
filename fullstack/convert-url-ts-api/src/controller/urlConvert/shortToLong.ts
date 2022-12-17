import {Request,Response} from "express";
import ShortToLongService from "../../service/ShortToLongService";

export = async (req:Request,res:Response)=>{
    if(!req.body){
        res.status(415).send({code:'F001',msg:'请检查参数！'});
        return;
    }
    
    if(!req.body.hasOwnProperty('shortUrl')|| typeof(req.body.shortUrl) !== 'string' || req.body.shortUrl.trim().length ===0){
        res.status(415).send({code:'F001',msg:'请检查参数！'});
        return;
    }
    let reg:RegExp = /[a-zA-Z]+:[/][/][a-zA-Z._-]+[/][^s]{1}/;
    if(!reg.test(req.body.shortUrl)){
        res.status(415).send({code:'F002',msg:'域名校验失败!'});
        return;
    }
    let longUrl:string = await ShortToLongService.convertToLong(req.body.shortUrl);
    res.send({code:'0000',data:longUrl});
}
