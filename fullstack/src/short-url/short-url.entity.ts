import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class ShortUrl {
  // 主键id
  @PrimaryGeneratedColumn()
  id?: number;

  // 原始URL
  @Column('text')
  url: string;

  // 对url做crc32哈希值
  @Column('bigint')
  hash: number;

  // 短链code
  @Column('varchar')
  code: string;

  @Column('timestamp', { name: 'created_at' })
  createdAt: Date;

  constructor(url: string, hash: number, code?: string) {
    this.url = url;
    this.hash = hash;
    this.code = code;
    this.createdAt = new Date();
  }
}
