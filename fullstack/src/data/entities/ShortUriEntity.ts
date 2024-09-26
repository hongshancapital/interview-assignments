import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn } from 'typeorm';

@Entity()
export class ShortUriEntity {
    @PrimaryGeneratedColumn()
    id: number

    @Column({
        nullable: false,
    })
    url: string

    @Column()
    urlHash: string

    @Column({
        length: 8,
        unique: true,
        nullable: false
    })
    shortId: string

    @CreateDateColumn()
    createDate: number
}