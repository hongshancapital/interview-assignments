import express from 'express'
import shortDomainEntity from '../entity'

const hostPath = 'http://localhost:3000/domain/'
const router = express.Router()

// 接受短域名信息，返回长域名信息
router.post('/shortTolong', async (req, res)=> {
    const { shortUrl } = req.body
    const shortKey = shortUrl.slice(hostPath.length)
    try{
        const row:any = await shortDomainEntity.query(shortKey)
        if(row){
            res.send({code:0, data:row.url})
        }else{
            res.send({code:0, data:''})
        }
    }catch(err){
        res.send({code:0, data:''})
    }
})

// 接受长域名信息，返回短域名信息
router.post('/longToShort', async (req, res)=> {
    let { url } = req.body;
    const shortKey = await shortDomainEntity.insert(url)
    res.send({code:0, data:hostPath + shortKey})
})

export default router