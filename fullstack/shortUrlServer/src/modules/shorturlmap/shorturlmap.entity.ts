import { Column, Entity, PrimaryGeneratedColumn, BaseEntity } from 'typeorm';

// 这里可以修改表名
@Entity('shorturlmap')
export class ShortUrlMap extends BaseEntity {
    @PrimaryGeneratedColumn()
    id: number;

    @Column('text', { name: 'shortUrl' })
    shortUrl: string;

    @Column('text', { name: 'longUrl' })
    longUrl: string;

    @Column('datetime', { name: 'created_time' })
    createdTime: Date;

    @Column('datetime', { name: 'expired_time' })
    expireTime: Date;
}