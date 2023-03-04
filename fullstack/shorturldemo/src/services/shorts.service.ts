import DB from '@databases';
import { HttpException } from '@exceptions/HttpException';
import { isEmpty } from '@utils/util';
import { CreateShortDto } from '@/dtos/shorts.dto';
import { Short } from '@/interfaces/shorts.interface';
import md5 from 'md5';
import { isURL } from 'class-validator';
import murmur from 'murmurhash-js'
import { string10to62 } from '../utils/util';
import { Op } from 'sequelize';
import CacheService from './cache.service';
import { logger } from '@/utils/logger';
import { ShortModel } from '@/models/shorts.model';

class ShortService {
  public shorts = DB.Shorts;
  public cache = new CacheService();

  public async findShortByShortKey(shortKey: string): Promise<Short> {
    if (isEmpty(shortKey)) throw new HttpException(400, "shortKey is empty");

    const rv = this.cache.get(shortKey);
    const cached = this.cacheToShort(rv) as Short;
    if (!isEmpty(cached)) {
      return cached;
    }

    const findShort: Short = await this.shorts.findOne({ where: { shortKey } });
    if (!findShort) throw new HttpException(409, "Short doesn't exist");

    this.cacheShort(findShort as Short);

    return findShort;
  }

  public async createShort(shortData: CreateShortDto): Promise<Short> {
    if (isEmpty(shortData)) throw new HttpException(400, "shortData is empty");

    const md5Str = md5(shortData.origin);
    const rv = this.cache.get(md5Str);
    const cached = this.cacheToShort(rv) as Short;
    if (!isEmpty(cached)) {
      return cached;
    }

    const shortKey = this.short(shortData.origin);

    const findShort: Short = await this.shorts.findOne({ where: { [Op.or]: [{ md5: md5Str }, { shortKey }] } });
    if (findShort && md5Str === findShort.md5) {
      this.cacheShort(findShort as Short);
      // `This url ${shortData.origin} already exists`;
      return findShort
    }
    if (findShort && shortKey === findShort.shortKey) {
      // 走到这里代表，生成的shortKey重复了，理论上：要再查一遍，判断如果不存在才继续，若还是存在则继续上一步。
      // But，考虑到实际情况，生成算法重复概率< 1/62**8;（不考虑随机seed的情况）
      // 所以暂时不为了这么低的概率消耗性能，直接给前端返回错误。前端可以再次调用。
      // 如果还是觉得不妥，可以在seed获取时做文章，变成自增的seed，这样就不可能重复了。
      throw new HttpException(400, "generated failed, please try again.");
    }

    // const hashedPassword = await hash(shortData.password, 10);
    const createShortData: Short = await this.shorts.create({ ...shortData, md5: md5Str, shortKey });

    this.cacheShort(createShortData);
    return createShortData;
  }

  getSeed() {
    // 这里可以使用两种方案进行生成非重复的seed： by:zhangwen9229
    // 1、调用分布式发号器(Distributed ID Generator)
    // 2、利用Redis的单线程自增函数
    return Math.random() * (10 ** 18);
  }

  short(origin: string) {

    if (!isURL(origin)) { throw new HttpException(400, 'origin is empty') }

    let v = murmur.murmur3(origin, this.getSeed());
    v = Math.abs(v);
    const vTransfer = (v * (Math.random())).toString().replace('.', '').split('').slice(0, 15).join('');

    const short = string10to62(Number(vTransfer))
    return short.slice(0, 8);
  }

  cacheToShort(rv: string): Short | void {
    if (!isEmpty(rv)) {
      try {
        const short = JSON.parse(rv) as Short;
        return short;
      } catch (error) {
        // 异常数据不能阻塞业务正常执行。
        logger.error('createShort error', error);
      }
    }
  }

  cacheShort(short: Short) {
    this.cache.set(short.md5, short);
    this.cache.set(short.shortKey, short);
  }
}

export default ShortService;
