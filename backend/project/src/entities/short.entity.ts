import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  PrimaryGeneratedColumn,
} from 'typeorm';

@Entity('shorts')
export class ShortEntity {
  // 主键
  @PrimaryGeneratedColumn()
  id: number;

  // 看需求
  // md5: string;

  @Column({
    type: 'varchar',
    length: 8,
  })
  // 增加索引
  @Index({ unique: true })
  code: string;

  @Column({
    type: 'text',
  })
  url: string;

  @CreateDateColumn({ type: 'timestamp' })
  created: Date;
}
