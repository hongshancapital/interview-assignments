import { Entity, PrimaryGeneratedColumn, Column } from "typeorm";


@Entity()
export class shorturl {
    @PrimaryGeneratedColumn()
    public shorturlid: string | undefined;

    @Column()
    public longurl: string | undefined;

    @Column()
    public createdata: string | undefined;
}