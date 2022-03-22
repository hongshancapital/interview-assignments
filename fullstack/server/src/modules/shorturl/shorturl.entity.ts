import {
  Column,
  Entity,
} from 'typeorm';
import { ApiProperty } from '@nestjs/swagger';

import { CommonEntity } from '../../common/common.enty'

@Entity('shorturl')
export class ShorturlEntity extends CommonEntity {
  @ApiProperty()
  @Column({
    comment: '原域名',
    nullable: false,
    unique: true,
  })
  url: string;

  @ApiProperty()
  @Column({
    comment: '短域名',
    default: ''
  })
  // 注意字节数
  s_url?: string;

  @ApiProperty()
  @Column({
    comment: '短域名描述',
    type: 'text',
    nullable: true
  })
  desc?: string | null;

  @ApiProperty()
  @Column({
    comment: '访问次数',
    default: 0
  })
  visit?: number;

  @ApiProperty()
  @Column({
    comment: '0：删除 1：开启',
    default: 1
  })
  // 注意长度
  status?: boolean;
}
