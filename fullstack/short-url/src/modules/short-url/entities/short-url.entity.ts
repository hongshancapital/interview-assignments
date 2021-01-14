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

  @Index('uni-hash', {
    unique: true,
  })
  @Column({
    type: 'char',
    width: 32,
    comment: 'Hash Value',
  })
  hash: string;

  @Column({
    type: 'text',
    comment: 'URL',
  })
  url: string;

  @CreateDateColumn({
    type: 'datetime',
    default: () => 'CURRENT_TIMESTAMP',
    comment: 'Create Time',
  })
  createdAt: Date;

  @UpdateDateColumn({
    type: 'datetime',
    default: () => 'CURRENT_TIMESTAMP',
    comment: 'Update Time',
  })
  updatedAt: Date;

  @Index('idx-deletedAt')
  @Column({
    type: 'datetime',
    default: null,
    comment: 'Delete Time',
  })
  deletedAt: Date;
}
