import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  PrimaryColumn,
  PrimaryGeneratedColumn,
} from "typeorm";

@Entity({
  name: "short_url",
})
export class ShortURL {
  @PrimaryGeneratedColumn()
  id!: number;

  @Index()
  @PrimaryColumn()
  sign!: String;

  @Column()
  url!: String;

  @PrimaryColumn({
    name: "short_link",
  })
  @Index()
  shortLink!: String;

  @CreateDateColumn({
    name: "create_time",
  })
  createTime!: Date;
}
