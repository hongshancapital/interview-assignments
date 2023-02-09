import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';
import { CreateDateColumn } from 'typeorm';

@Entity("Test")
export class UrlConvert {

  @PrimaryGeneratedColumn() //主键，自增
  id: number;

  @Column()
  short_url: string;

  @Column({nullable:true})
  long_url: string;

  @CreateDateColumn({ nullable:true})
  createTime:Date;
}