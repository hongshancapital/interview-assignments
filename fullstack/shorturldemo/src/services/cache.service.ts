import { compare, hash } from 'bcrypt';
import { sign } from 'jsonwebtoken';
import { SECRET_KEY } from '@config';
import DB from '@databases';
import { CreateUserDto } from '@dtos/users.dto';
import { HttpException } from '@exceptions/HttpException';
import { DataStoredInToken, TokenData } from '@interfaces/auth.interface';
import { User } from '@interfaces/users.interface';
import { isEmpty } from '@utils/util';
import cache from 'memory-cache';

export type CacheOption = {
  expiresIn?: number,
  timeoutFn?: (key: string, value: any) => any
}

class CacheService {
  public set(key: string, value: any, opt: CacheOption = { expiresIn: 60 * 60 * 1000 }) {
    const { expiresIn } = opt;
    let newValue = value;
    if (typeof value === 'object') {
      newValue = JSON.stringify(value);
    }
    cache.put(key, newValue, expiresIn);
  }

  public get(key: string) {
    return cache.get(key);
  }
}

export default CacheService;
