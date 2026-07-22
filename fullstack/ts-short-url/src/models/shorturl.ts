import { Entity, PrimaryGeneratedColumn, Column, PrimaryColumn } from "typeorm";

// 类和成员名称要保持和数据库信息一致
@Entity("short_url")
export class short_url {
    @PrimaryColumn()
    public shorturl_id!: string;

    @Column()
    public original_url!: string;

    @Column()
    public create_date!: string;
}