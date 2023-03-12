import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn, BaseEntity, Index } from 'typeorm';

@Entity({ name: 'short_url' })
export class ShortUrlEntity extends BaseEntity {
    @PrimaryGeneratedColumn()
    id: number;

    @Index({ unique: true })
    @Column({
        name: 'short_url',
        comment: '短链接',
        type: 'varchar',
        length: 50,
        nullable: false
    })
    shortUrl: string;

    @Column({
        name: 'long_url',
        comment: '原始长链接',
        type: 'varchar',
        length: 200,
        nullable: false
    })
    longUrl: string;

    @Column({
        name: 'expired_at',
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
