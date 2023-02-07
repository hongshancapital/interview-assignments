import { Entity, Column, Index, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn } from "typeorm";

@Entity('shorturls',{ synchronize:true })
// 长短链接都需要添加索引
@Index(['shortUrl', 'longUrl'], { unique: true })
export class ShortUrl {

  @PrimaryGeneratedColumn()
  id!: number

  @CreateDateColumn({
    name: 'created_at',
    type: 'timestamp',
  })
  createdAt!: Date;

  @UpdateDateColumn({
    name: 'updated_at',
    type: 'timestamp',
  })
  updatedAt!: Date;

  @Column({
    name: 'short_url',
    type: 'varchar',
    length: 8,
  })
  shortUrl!: string;

  @Column({
    name: 'long_url',
    type: 'varchar',
    length: 4096,
  })
  longUrl!: string;

  public static ValueOf(options: {
    shortUrl: string,
    longUrl: string,
  }) {
    const url = new ShortUrl();
    const { shortUrl, longUrl } = options;
    url.shortUrl = shortUrl;
    url.longUrl = longUrl;
    return url;
  }
}