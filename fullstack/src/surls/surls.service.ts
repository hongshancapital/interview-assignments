import { Injectable, HttpException, HttpStatus } from '@nestjs/common';
import { InjectModel } from '@nestjs/sequelize';
import { Surl } from './surl.model';
import { createClient } from 'redis';
const murmurhash = require("murmurhash");
const base62 = require("base62");

let client = null;

async function bloomFiter() {
  try {
    client = createClient({
      url: 'redis://127.0.0.1:6379',
    });
    client.on('error', (err) => console.log('Redis Client Error', err));
    await client.connect();
    
    await client.bf.reserve('bf', 0.01, 10000);
  } catch(e) {
    console.log("bf filter was exists");
  }
}

// bloomFiter()

const KEYWORD = 'helloworld';
interface SurlColumn {
  surl: string;
  longUrl: string;
  kword: string;
}

@Injectable()
export class SurlsService {
  constructor(
    @InjectModel(Surl)
    private surlModel: typeof Surl
  ) {}

  findOne(surl: string): Promise<Surl> {
    try {
      return this.surlModel.findOne({
        where: {
          surl,
        },
      });
    } catch (error) {
      throw new HttpException({
        status: HttpStatus.INTERNAL_SERVER_ERROR,
        error: '短链接数据库查询失败',
      }, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  async findSurlByLongUrl(longUrl: string): Promise<Surl> {
    return this.surlModel.findOne({ 
      where: { 
        longUrl 
      } 
    });
  }

  async generateModel(longUrl: string, kword: string): Promise<SurlColumn> {
    let lurl = longUrl;
    const murmurhashStr = '' + murmurhash.v3(lurl, kword);
    let surl = base62.encode(murmurhashStr);
    const isExist = await this.checkUrlExist(surl);

    // 判断生成的 surl 是否重复
    if(!isExist) {
      return {
        surl,
        kword,
        longUrl: lurl + kword
      };
    } else {
      await this.generateModel(lurl, kword + KEYWORD);
    }
  }

  // 检查 url 是否存在
  async checkUrlExist(url: string): Promise<boolean> {
    try {
      const isExist = await client.bf.exists('bf', url);
      if(isExist) {
        return true;
      } else {
        return false;
      }
    } catch(e) {
      console.log('check url exist error');
      return false;
    }
  }

  async create(longUrl: string, surl: string, kword): Promise<Surl> {
    return this.surlModel.create({
      longUrl,
      surl,
      kword
    });
  }

  // 数据缓存到 redis
  async setKey(surl: string, cache: SurlColumn) {
    const key = `${surl}`;
    const value = `${JSON.stringify(cache)}`;
    await client.set(key, value);
  }

  // 读取缓存里的 url
  async getUrlFromCache (surl: string): Promise<string> {
    const key = `${surl}`;
    const value = await client.get(key);
    return value;
  }

  async getLongUrl(surl: string): Promise<string> {
    if(client) {
      const cacheOne = await this.getUrlFromCache(surl);
      if(cacheOne) {
        try {
          const cache = JSON.parse(cacheOne);
          return cache.longUrl;
        } catch {
          const { longUrl } = await this.findOne(surl);
          return longUrl;
        }
      }
    }

    const surlObj = (await this.findOne(surl) || {}) as SurlColumn;
    const { longUrl, kword } = surlObj as SurlColumn;
    if(longUrl && client) {
      await this.setKey(surl, surlObj);
      return longUrl;
    }

    if(!longUrl) {
      throw new HttpException({
        status: HttpStatus.INTERNAL_SERVER_ERROR,
        error: '短链接不存在',
      }, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // 如果存在 keyword 则删除 keword
    if(kword) {
      const len = kword.length;
      return `${longUrl.slice(0, -len)}`;
    }
    return `${longUrl}`;
  }

  isValidLongUrl(longUrl: string): boolean {
    if(longUrl.startsWith('http://') || longUrl.startsWith('https://')) {
      return true;
    }
    return false;
  }

  async LongToShort(longUrl: string): Promise<string> {
    if(this.isValidLongUrl(longUrl)) {
      const isExist = await this.checkUrlExist(longUrl);
      if(!isExist) {
        const { surl, longUrl: lurl, kword } = await this.generateModel(longUrl, '');
        try {
          await client.bf.add('bf', surl);
        } catch(e) {
          console.log('add bf error');
        }

        await this.create(lurl, surl, kword);
        return `${surl}`;
      } else {
        const { surl } = await this.findSurlByLongUrl(longUrl)
        return `${surl}`;
      }
    } else {
      throw new HttpException({
        status: HttpStatus.FORBIDDEN,
        error: '参数错误，url 链接不合法，必须以 http 或 https 开头',
      }, HttpStatus.FORBIDDEN);
    }
  }
}