import {
  Entity,
  Column,
  CreateDateColumn,
  PrimaryGeneratedColumn,
} from "typeorm";

@Entity("short_url_map")
export class Url {
  @PrimaryGeneratedColumn()
    id: number;

  @Column({ name: "original_url" })
    originalUrl: string;

  @Column({ name: "short_url" })
    shortUrl: string;

  @Column({ name: "url_hash" })
    urlHash: string;

  @CreateDateColumn({ name: "created_at" })
    createdAt: number;
}
