import { Entity, PrimaryGeneratedColumn, Column,Index } from "typeorm"

@Entity()
export class ShortUrlMap {

    @PrimaryGeneratedColumn()
    id: number

    @Column()
    shortUrl: string

    @Index()
    @Column()
    longUrl: string

    @Index()
    @Column()
    shortHash: string

}
