import { CreateDateColumn } from 'typeorm';

export abstract class BaseEntity {
  @CreateDateColumn({
    name: 'ctime',
    type: 'timestamp',
  })
  ctime: Date;
}
