import {
    Entity,
    PrimaryGeneratedColumn,
    Column,
    CreateDateColumn,
} from "typeorm"
@Entity()
export class ShortUrl {
    @PrimaryGeneratedColumn()
    id?: number

    @Column({ unique: true })
    long_url?: string

    @CreateDateColumn()
    createTime?: Date

    static LONG_URL_MAX_LENGTH = 2083

}
