
import {
  Column,
  CreateDateColumn,
  Entity,
  PrimaryGeneratedColumn,
} from 'typeorm';

@Entity()
export class ShortUrlRepository {
  /***
   * 主键
   */
  @PrimaryGeneratedColumn({ comment: '主键' })
  id: number;

  /***
   * 短链接
   * @type {string}
   * @memberof ShortUrlRepository
   * @example https://short-url.com/12345678
   * @description 短链接的长度为8，由数字和小写字母组成
   * @description 短链接的长度可以通过修改MAX_SHORT_URL_LENGTH来修改
   * @description 短链接的域名可以通过修改DOMAIN来修改
   */
  @Column({ length: 64, nullable: false, comment: '短链接' })
  shortUrl: string;

  /***
   * 长链接
   * @type {string}
   * @memberof ShortUrlRepository
   * @example https://www.google.com/
   */
  @Column({ length: 128, nullable: false, comment: '长链接' })
  longUrl: string;

  /***
   * 创建时间
   * @type {Date}
   * @memberof ShortUrlRepository
   * @description 该字段由数据库自动创建
   * @description 该字段不可修改
   * @description 该字段不可为空
   */
  @CreateDateColumn({ nullable: false, comment: '创建时间' })
  create_time: Date;
}
