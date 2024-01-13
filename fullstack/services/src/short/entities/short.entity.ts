import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Short {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  short_key: string;

  @Column({ unique: true })
  long_url: string;

  @Column({type: 'timestamp', default: () => "CURRENT_TIMESTAMP"})
  create_time: Date;
}
