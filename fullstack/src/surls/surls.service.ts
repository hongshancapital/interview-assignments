import { Injectable, HttpException, HttpStatus } from '@nestjs/common';
import { InjectModel } from '@nestjs/sequelize';
import { Surl } from './surl.model';
const murmurhash = require("murmurhash");
const base62 = require("base62");
var jsbloom = require("@duckduckgo/jsbloom")
const filter = jsbloom.filter(1000000, 0.00001);

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

  async findAll(): Promise<Surl[]> {
    return this.surlModel.findAll();
  }

  findOne(surl: string): Promise<Surl> {
    return this.surlModel.findOne({
      where: {
        surl,
      },
    });
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
    const isExist = this.checkUrlExist(surl);

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

    filter.addEntry(lurl);
  }

  // 检查 url 是否存在
  checkUrlExist(url: string): boolean {
    if(filter.checkEntry(url)) {
      return true;
    } else {
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

  async getLongUrl(surl: string): Promise<string> {
    const { longUrl, kword } = await this.findOne(surl);
    
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
      if(!this.checkUrlExist(longUrl)) {
        const { surl, longUrl: lurl, kword } = await this.generateModel(longUrl, '');

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