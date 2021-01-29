import { Column, CreateDateColumn, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { Track } from "./track.entity";

@Entity()
export class ShortLink {
  @PrimaryGeneratedColumn()
  id: number;
  //原始连接
  @Column()
  sourceLink: string;
  //生成连接
  @Column()
  generateLink: string;
  //创建时间
  @CreateDateColumn()
  created: Date;
  //过期时间
  @Column()
  expired: Date;
  @OneToMany(type => Track, o => o.shortLink)
  tracks: Track[];
}