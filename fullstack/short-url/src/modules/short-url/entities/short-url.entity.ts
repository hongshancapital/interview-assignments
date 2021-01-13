import {
  Entity,
  Column,
  UpdateDateColumn,
  CreateDateColumn,
  PrimaryColumn,
  Index,
} from 'typeorm';

@Entity()
export class ShortUrl {
  @PrimaryColumn({
    type: 'bigint',
    unsigned: true,
    width: 20,
    comment: 'ID',
  })
  id: number;

  @Index('idx-url')
  @Column({
    type: 'varchar',
    width: 2083,
    comment: 'URL',
  })
  url: string;

  @CreateDateColumn({
    type: 'datetime',
    precision: null,
    default: () => 'CURRENT_TIMESTAMP',
    comment: 'Create Time',
  })
  createdAt: Date;

  @UpdateDateColumn({
    type: 'datetime',
    precision: null,
    default: () => 'CURRENT_TIMESTAMP',
    comment: 'Update Time',
  })
  updatedAt: Date;

  @Index('idx-deletedAt')
  @Column({
    type: 'datetime',
    precision: null,
    default: null,
    comment: 'Delete Time',
  })
  deletedAt: Date;
}
