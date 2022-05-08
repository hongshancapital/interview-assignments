import {Request, Response} from 'express';
import Validator from 'validator';
import MongdbInstance from '../services/db/Mongoose';
import RedisInstance from '../services/db/RedisCache';
import Mysqlinstance from '../services/db/Sequelize';
import SnowflakeIdinstance from '../services/SnowflakeId';

class TinyurlController {
    async GetTinyurl(req: Request, res: Response) {
        const {shortId} = req.params;
        try {
            //get url from redis
            const Redisrecord = await RedisInstance.get(shortId);
            if (Redisrecord !== null) {
                return res.status(200).json(Redisrecord);
            }

            //get url from mongodb & set result to redis
            const record = await MongdbInstance.getUrl({shortId});
            if (!record) {
                return res.status(400).json({message: 'shortId is invalid'});
            }

            await RedisInstance.set(shortId, record.url);
            return res.status(200).json(record.url);
            //return res.redirect(record.url);
        } catch (error) {
            //console.log(error);
            return res.status(500).json({message: 'Some thing went wrong!'});
        }
    }

    async TinyUrl(req: Request, res: Response) {
        const {url} = req.body;
        if (!url) {
            return res.status(400).json({message: 'url is not provided'});
        }

        if (!Validator.isURL(url, {require_protocol: true})) {
            return res.status(400).json({message: 'url is invalid'});
        }
        try {
            //check if url & id exist in mongodb
            const record = await MongdbInstance.getUrl({url});
            if (record) {
                return res.status(200).json({url, shortId: record.shortId});
            }
            const segmentId = Number(await Mysqlinstance.dequeue());
            const shortId = SnowflakeIdinstance.getId(segmentId, false);
            //get shortid, if the length shortid exceed 8 bits,return 500
            if (shortId == '-1') {
                return res.status(500).json({message: 'The length of Tiny url exceed 8 bits'});
            }
            //store shortid & url in mongodb & redis
            const newUrl = {
                url,
                shortId: shortId,
            };
            //return res.status(500).json({message: 'debug...'});
            await MongdbInstance.createUrl(newUrl);
            await RedisInstance.set(shortId, url);
            return res.status(200).json(newUrl);
        } catch (error) {
            //console.log(error);
            return res.status(500).json({message: 'Some thing went wrong!'});
        }
    }
}

export default new TinyurlController();
