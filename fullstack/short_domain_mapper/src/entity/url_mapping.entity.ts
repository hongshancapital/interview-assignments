import { Entity, Column, PrimaryColumn, BaseEntity } from 'typeorm';

@Entity({
  name: 'url_mapping',
})
export class UrlMapping extends BaseEntity {
  @PrimaryColumn()
  short_url!: string;

  @Column()
  full_url!: string;

  @Column()
  salt!: string;
}
