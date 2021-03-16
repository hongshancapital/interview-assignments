import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from "typeorm";
import { ShortLink } from "./short-link.entity";

@Entity()
export class Track {
  @PrimaryGeneratedColumn()
  id: number;
  @ManyToOne(type => ShortLink)
  shortLink: ShortLink;
  @Column()
  shortLinkId: number;
  @Column()
  uuid: string;
  @Column({ nullable: true })
  ip: string;
  @Column({ nullable: true })
  userAgent: string;
  @Column({ nullable: true })
  referer: string;
  @Column({ nullable: true })
  language: string;
}