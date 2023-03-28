import { Entity, Column, PrimaryGeneratedColumn, Index } from 'typeorm';

@Entity({name: 't_url'})
export class UrlEntity {
    constructor(shortUrl: string, longUrl: string) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    @PrimaryGeneratedColumn()
    public id: number;

    @Index('idx_short_url', { unique: true })
    @Column('varchar', { name: 'short_url', length: 8, nullable: false, default: '' })
    public shortUrl: string;

    // long_url手动创建一个前缀索引, 因此不使用orm entity来同步索引的定义
    @Index('idx_long_url', { synchronize: false })
    @Column('text', {  name: 'long_url' })
    public longUrl: string;
}
