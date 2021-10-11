import { Entity, Column, PrimaryColumn } from 'typeorm';

@Entity()
export class Url {
  constructor(shortUrl: string, longUrl: string) {
    this.shortUrl = shortUrl;
    this.longUrl = longUrl;
  }

  @PrimaryColumn('varchar', { nullable: false })
  public shortUrl: string;

  @Column('text', { nullable: false })
  public longUrl: string;
}
