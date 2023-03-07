import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn } from 'typeorm';

@Entity()
export class ShortUrl {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({
        comment: '短链接',
        type: 'varchar',
        length: 50,
        nullable: false
    })
    shortUrl: string;

    @Column({
        comment: '原始长链接',
        type: 'varchar',
        length: 200,
        nullable: false
    })
    longUrl: string;

    @CreateDateColumn({
        name: 'created_at',
        type: 'timestamp',
        comment: '创建时间'
    })
    createdAt: Date;

    @UpdateDateColumn({
        name: 'updated_at',
        type: 'timestamp',
        comment: '更新时间'
    })
    updatedAt: Date;
}
