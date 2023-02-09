import { getModelForClass, ReturnModelType } from "@typegoose/typegoose";
import { ShortUrl } from "../database/shortUrl.model";
import { myUtil } from "../utils/myUtil";

export class ShortUrlService {
  private count: number;
  private shortUrlModel: ReturnModelType<typeof ShortUrl>;
  constructor() {
    this.shortUrlModel = getModelForClass(ShortUrl);
    this.count = 0; //用于递增生成唯一ID
  }

  async getLatestCount(): Promise<number> {
    //服务重启后会通过查询最新一条记录，恢复计数器count的值
    const latestUrl = await this.shortUrlModel.findOne({}).sort({ _id: -1 });
    const base62Str = latestUrl
      ? latestUrl.shortUrl.split("/").pop()
      : "12345677";
    return myUtil.base62StringToNumber(base62Str);
  }

  async saveLongUrl(longUrl: string): Promise<string> {
    try {
      // 1.校验longUrl是否符合网址规范
      if (!myUtil.validateUrl(longUrl)) return "";

      // 2.检查longUrl是否已存在
      const fetchRes = await this.shortUrlModel.findOne({ longUrl });
      if (fetchRes) return fetchRes.shortUrl;

      // 3.生成shortUrl
      if (this.count == 0) {
        this.count = (await this.getLatestCount()) + 1;
      }
      let shortUrl: string = myUtil.genShortUrl(this.count++);
      // while (await this.shortUrlModel.findOne({ shortUrl })) {
      //   shortUrl = myUtil.genShortUrl(this.count++);
      // }

      // 4.插入数据库
      const createRes = await this.shortUrlModel.create({ longUrl, shortUrl });
      return createRes.shortUrl;
    } catch (err) {
      console.error("Insert LongUrl Error!", err);
      return "";
    }
  }

  async fetchLongUrl(shortUrl: string): Promise<string> {
    const findRes = await this.shortUrlModel.findOne({ shortUrl });
    return findRes ? findRes.longUrl : "";
  }
}
