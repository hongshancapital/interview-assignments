import {
  BaseEntity,
  BeforeInsert,
  Column,
  Entity,
  PrimaryGeneratedColumn,
} from 'typeorm';
import { ApiProperty } from '@nestjs/swagger';

import { Expose } from 'class-transformer';


@Entity('shorturl')
export class ShorturlEntity extends BaseEntity {
  @ApiProperty()
  @Expose()
  @PrimaryGeneratedColumn({
    name: 'id',
    type: 'bigint',
    comment: '唯一ID'
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
    default: 1
  })
  // 注意长度
  status?: boolean;
}
