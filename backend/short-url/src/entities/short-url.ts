import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn, BaseEntity } from 'typeorm';

@Entity()
export class ShortUrl extends BaseEntity {
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

    @Column({
        comment: '过期时间',
        type: 'timestamp',
        nullable: true
    })
    expiredAt: Date | null;

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
