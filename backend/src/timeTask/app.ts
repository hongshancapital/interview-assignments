import Redis from 'ioredis';
import TimedTask from "../util/TimedTask";
import config from '../config/config';
import ShortUrlService from '../service/ShortUrlService';
import Util from "../util/Util";
let mongoose =  require('mongoose');
mongoose.connect(config.mongodb)

const shortUrlServiceModel  = new ShortUrlService();
const redis = new Redis(config.redis);



import shortKeyPoolTask  from './timeTask/shortKeyPoolTask';


//每秒检查缓存池里shortKey数量，并保证缓存的key数量在某个比例
new TimedTask(
    {callback:Util.defineListener( async()=>{
        await shortKeyPoolTask(redis,shortUrlServiceModel);
    }),
        interval:1000,
        startAt:new Date(Date.now() +2000)
    });