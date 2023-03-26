import { Entity, PrimaryGeneratedColumn, Column } from 'typeorm';

@Entity('url_mapping')
export class UrlMapping {

  constructor(shortUrl: string, longUrl: string) {
    this.shortUrl = shortUrl;
    this.longUrl = longUrl;
  }

  @PrimaryGeneratedColumn()
    id!: number;

  @Column('datetime', { name: 'create_time' })
    createTime!: number;

  @Column('datetime', { name: 'update_time' })
    updateTime!: number;

  @Column('varchar', { name: 'short_url' })
    shortUrl!: string;

  @Column('varchar', { name: 'long_url' })
    longUrl!: string;

}
