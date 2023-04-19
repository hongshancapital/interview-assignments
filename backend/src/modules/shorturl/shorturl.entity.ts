import {
  BaseEntity,
  Column,
  Entity,
} from 'typeorm';
import { ApiProperty } from '@nestjs/swagger';

import { Expose } from 'class-transformer';


@Entity('shorturl')
export class ShorturlEntity extends BaseEntity {
  @ApiProperty()
  @Expose()
  @Column({
    name: 'id',
    type: 'decimal',
    // 数字长度限制在 ZZZZZZZZ 最长8位字符
    precision: 15,
    scale: 0,
    comment: '唯一ID',
    generated: 'increment',
  })
  id?: string;

  @ApiProperty()
  @Column({
    comment: '原域名',
    type: 'varchar',
    length: 999,
    // 长域名查重
    unique: true,
    nullable: false
  })
  url: string;

  @ApiProperty()
  @Column({
    comment: '0：删除 1：开启',
    type: 'tinyint',
    default: 1
  })
  status?: boolean;
}
