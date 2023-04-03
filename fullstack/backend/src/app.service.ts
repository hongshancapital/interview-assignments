import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Record } from './record.entity';
import { Repository } from 'typeorm';
import { nanoid } from './utils';
@Injectable()
export class AppService {
  constructor(
    @InjectRepository(Record)
    private recordsRepository: Repository<Record>,
  ) {}

  async shortToFull(slug: string) {
    const target = await this.recordsRepository.findOneByOrFail({ slug });
    return target.content;
  }

  async createRecord(url: string) {
    const slug = nanoid(8);
    const record = new Record();
    record.content = url;
    record.slug = slug;
    return this.recordsRepository.save(record);
  }
}
