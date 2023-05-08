import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { ShorturlEntity } from './shorturl.entity';
import { CrudService } from '../../common/crud.service';

@Injectable()
export class ShorturlService extends CrudService<ShorturlEntity> {
  constructor(
    @InjectRepository(ShorturlEntity) protected repository: Repository<ShorturlEntity>
  ) {
    super();
  }
}
