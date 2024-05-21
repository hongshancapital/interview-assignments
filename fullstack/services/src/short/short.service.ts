import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm'
import { Repository } from 'typeorm'
import { Short } from './entities/short.entity'

@Injectable()
export class ShortService {
  constructor(
    @InjectRepository(Short) private shortRepository: Repository<Short>
  ) {}

  create(long_url: string, short_key: string) {
    return this.shortRepository.save({
      long_url,
      short_key
    })
  }

  findAll() {
    return this.shortRepository.find()
  }

  findByUrl(long_url: string) {
    return this.shortRepository.findOneBy({ long_url });
  }

  findByShortKey(short_key: string) {
    return this.shortRepository.findOneBy({ short_key });
  }
}
