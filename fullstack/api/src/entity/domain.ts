import { Entity, Column, CreateDateColumn, UpdateDateColumn, PrimaryColumn, Index } from "typeorm";

@Entity()
export class Domain {
    @PrimaryColumn({ type: "varchar", length: 10 })
    id!: string;

    @Column({ type: "varchar", length: 200, unique: true })
    domain!: string;

    @Column({ type: "varchar", length: 8, unique: true })
    url!: string

    @CreateDateColumn({ select: false })
    createTime?: Date;

    @UpdateDateColumn({ select: false })
    updateTime?: Date;
}