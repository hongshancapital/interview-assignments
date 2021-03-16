import { Column, CreateDateColumn, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { Track } from "./track.entity";

@Entity()
export class ShortLink {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  sourceLink: string;

  @Column()
  generateLink: string;

  @CreateDateColumn()
  created: Date;

  @Column()
  expired: Date;
  @OneToMany(type => Track, o => o.shortLink)
  tracks: Track[];
}