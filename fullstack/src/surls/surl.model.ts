import { Column, Model, Table } from 'sequelize-typescript';

@Table
export class Surl extends Model<Surl> {
  @Column
  surl: string;

  @Column
  longUrl: string;

  @Column
  kword: string;
}