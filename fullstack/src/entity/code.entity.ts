import {
  Entity,
  Column,
  BaseEntity,
  PrimaryGeneratedColumn,
  Index,
} from 'typeorm';

@Entity({
  name: 'code',
})
export class Code extends BaseEntity {
  @PrimaryGeneratedColumn('increment')
  id: number;

  @Index({
    unique: true,
  })
  @Column({
    comment: '短码',
  })
  code: string;

  @Column({
    comment: '正常url',
    unique: true,
  })
  url: string;
}
