import { Request } from 'express';
import LongToShortService from '../../service/LongToShortService' 
module.exports = async (req: Request, res: any) => {
    if(!req.body){
        res.status(415).send({code:'F001',msg:'请检查参数！'});
        return;
    }
    
    if(!req.body.hasOwnProperty('longUrl')|| typeof(req.body.longUrl) !== 'string' || req.body.longUrl.trim().length ===0){
        res.status(415).send({code:'F001',msg:'请检查参数！'});
        return;
    }
    let reg:RegExp = /[a-zA-Z]+:[/][/][a-zA-Z._-]+[/].*/;
    if(!reg.test(req.body.longUrl)){
        res.status(415).send({code:'F002',msg:'域名校验失败!'});
        return;
    }
    //TODO 校验郁闷合法性

    return res.send({code: '0000', data:await LongToShortService.convertToShort(req.body.longUrl as string)});
}