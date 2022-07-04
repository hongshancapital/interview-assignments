import { Entity, Column, PrimaryColumn } from 'typeorm';
import { BaseEntity } from './base.entity';

@Entity({ name: 'sl_link' })
export class LinkEntity extends BaseEntity {
  @PrimaryColumn({ type: 'varchar', length: 8 })
  id: string;

  @Column({ type: 'text' })
  link: string;
}
