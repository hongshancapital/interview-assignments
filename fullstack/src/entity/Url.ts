import { Entity, PrimaryGeneratedColumn, Column } from "typeorm"

export const MAX_URL_LENGTH = 9000;

@Entity()
export class Url {

    @PrimaryGeneratedColumn()
    id: number

    @Column({
      length: MAX_URL_LENGTH
    })
    original: string

}

