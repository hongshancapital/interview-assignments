import {Redis} from  'ioredis';
import config from "../../config/config";
import Util from "../../util/Util";
import ShortUrlService from '../../service/ShortUrlService';
const shortKeyPoolTask = async  function (redis:Redis,shortUrlServiceModel : ShortUrlService) {
    let total = await redis.scard(config.rediskey.shortIdPool);
    if(total === 0){ //初始池子里shortKey为0个的时候
        await genShortKeys(config.poolMaxNumber,shortUrlServiceModel,redis);
    }else if(total < config.poolMaxNumber * config.poolMinRate){
        await genShortKeys(config.genCountEveryTime,shortUrlServiceModel,redis);
    }


}

async function genShortKeys(count:number, shortUrlServiceModel : ShortUrlService, redis:Redis) {
    try{
        let target = 0;
        let keys : string[]= [];
        while(target < count){
            let key = Util.generateShortId();
            try{
                await shortUrlServiceModel.addShortUrl(key);
                keys.push(key)
                target += 1;
            }catch (e) {
                console.log(e)
            }

        }
        try{
            await redis.sadd(config.rediskey.shortIdPool,keys);
        }catch (e) {
            console.log(e)
        }

    }catch (e) {
        console.log(e)
    }



}


export default shortKeyPoolTask;