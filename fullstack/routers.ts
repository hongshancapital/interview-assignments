import express from 'express';
import Url from './model/shortUrl';
import Utils from './utils';
const router: express.Router = express.Router();
const baseurl = 'localhost:8000/surl/';
router.post('/',async (req,res)=>{
    try {
        let longurl = req.body.url;
        if(!Utils.checkLongUrl(longurl)){
            return res.send({
              code: 101,
              msg: 'url参数不合法！'  
            })
        }else{
            let u:any = await Url.findOne({longurl})
            if(u){
                return res.send({
                    code: 200,
                    msg: '链接已存在',
                    shorturl: `${baseurl}${u.urlid}`
                })
            }
            const sid = Utils.getShortID(4);
            const newurl = new Url({
                longurl: longurl,
                urlid: sid
              });
            newurl.save();
            return res.send({
                code: 200,
                msg: '添加成功！',
                shorturl: `${baseurl}${sid}`
            });
        }
        
    } catch (error) {
        console.error(error);
        return res.send({
            code: 500,
            msg: error.message
        })
    }
    
    
});
router.get('/:sid',async (req,res)=>{
    try {
        let urlid = req.params.sid;
        if(!urlid){
            return res.send({
            code: 101,
            msg: 'url不能为空！'  
            })
        }else{
            let u:any = await Url.findOne({urlid})
            if(u){
                return res.send({
                    code: 200,
                    msg: '获取成功！',
                    longurl: u.longurl
                })
            }else{
                return res.send({
                    code: 101,
                    msg: '不存在！'  
                })
            }
        }
    } catch (error) {
        console.error(error);
        return res.send({
            code: 500,
            msg: error.message
        })
    }
    
})
export default router;