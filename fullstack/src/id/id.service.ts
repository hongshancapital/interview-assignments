import { Injectable } from '@nestjs/common';
import { SnowflakeIdBit48 } from './id.snowflake.bit48';

@Injectable()
export class IdService {
  private idGenerator: SnowflakeIdBit48;
  private static Base62Chars =
    'abcdefghijklmnopqrstuvwxyz-ABCDEFGHIJKLMNOPQRSTUVWXYZ@0123456789'.split('');
  private static Base62Radix = IdService.Base62Chars.length;
  constructor() {
    this.idGenerator = new SnowflakeIdBit48({ workerId: 'unknown' });
  }

  protected nextSnowId(): bigint {
    return this.idGenerator.nextBigId();
  }

  public nextId(): string {
    const snowId = this.nextSnowId();
    const arr = [];
    let temp: bigint = snowId;
    do {
      const mod = temp % BigInt(IdService.Base62Radix);
      temp = (temp - mod) / BigInt(IdService.Base62Radix);
      arr.unshift(IdService.Base62Chars[Number(mod)]);
    } while (temp);
    return arr.join('');
  }
}
