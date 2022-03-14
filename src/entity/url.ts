import { Column, Model } from 'sequelize-typescript';
import { BaseTable } from '@midwayjs/sequelize';

@BaseTable({ modelName: 'urlmap', freezeTableName: true })
export class UrlModel extends Model {
  @Column({
    comment: 'id',
    primaryKey: true,
    unique: true,
    autoIncrement: true,
  })
  id: number;

  @Column({
    comment: '短链接',
  })
  shorturl: string;

  @Column({
    comment: '原始链接',
  })
  origin: number;
}
