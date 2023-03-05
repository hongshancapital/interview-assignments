import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  Unique,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';

@Entity('url')
@Unique('u_shortUrl', ['shortUrl'])
@Index('i_originUrl', ['originUrl'])
export class UrlEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @Column({
    type: 'varchar',
    length: 200,
    nullable: false,
    comment: '原Url',
  })
  originUrl: string;

  @Column({
    type: 'varchar',
    length: 100,
    nullable: true,
    comment: '私钥',
  })
  shortUrl: string;

  @CreateDateColumn()
  createAt: Date;

  @UpdateDateColumn()
  updateAt: Date;
}
