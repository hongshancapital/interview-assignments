import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class UrlMap {
    @PrimaryGeneratedColumn()
    id!: number;
    
    @Column({
        type: 'varchar',
        length: 1024,
        comment: '长地址'
    })
    long_url!: string
    
    // short_url 列上建立唯一索引
    @Column({
        type: 'varchar',
        length: 8,
        comment: '短地址'
    })
    short_url!: string
}