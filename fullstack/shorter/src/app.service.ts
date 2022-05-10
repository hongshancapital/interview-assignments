import { Injectable } from '@nestjs/common';
import { IdfactoryService } from './idfactory/idfactory.service';
import { KvstoreService } from './kvstore/kvstore.service';


@Injectable()
export class AppService {

  constructor(private readonly idfactory: IdfactoryService, private readonly kvstore: KvstoreService) { }

  async saveShortAndOriginURL(origin: string) {
    const id = this.idfactory.getNextId();
    // save to db
    await this.kvstore.setKV(id, origin);
    return id;
  }

  async getOriginURLByShortKey(shortURL: string) {
    const url = await this.kvstore.getValue(shortURL);
    return url;
  }
}
