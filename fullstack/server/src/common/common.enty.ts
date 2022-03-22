import { Column, UpdateDateColumn, CreateDateColumn, PrimaryGeneratedColumn, BaseEntity } from 'typeorm';
import { ApiProperty } from '@nestjs/swagger';
import { Exclude, Expose } from 'class-transformer';

export abstract class CommonEntity extends BaseEntity {
  @ApiProperty()
  @Expose()
  @PrimaryGeneratedColumn('uuid', {
    name: 'id',
    comment: '唯一ID',
  })
  id?: string;

  @ApiProperty()
  @Column({
    comment: '修改或者创建人',
    default: '',
  })
  @Exclude()
  updateId?: string;

  @CreateDateColumn({
    type: 'timestamp',
    precision: 3,
    default: () => 'CURRENT_TIMESTAMP(3)',
    comment: '创建时间'
  })
  created_time?: Date;

  @UpdateDateColumn({
    type: 'timestamp',
    precision: 3,
    default: () => 'CURRENT_TIMESTAMP(3)',
    onUpdate: 'CURRENT_TIMESTAMP(3)',
    comment: '修改时间'
  })
  @Exclude()
  updated_time?: Date;

  @Column({
    comment: '是否删除',
    default: 0
  })
  @Exclude()
  deleted?: number;
}


