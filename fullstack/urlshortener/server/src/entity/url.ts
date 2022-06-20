import { Column, CreateDateColumn, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Url {

  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column('text', { unique: true, nullable: false })
  code: string;

  @Column('text', { nullable: false })
  originalUrl: string;

  @CreateDateColumn({ type: 'timestamp with time zone', nullable: false })
  createTime: Date;
}
