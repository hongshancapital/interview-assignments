import {
  Entity,
  Column,
  ObjectID,
  ObjectIdColumn,
  CreateDateColumn,
  UpdateDateColumn,
} from "typeorm";

@Entity()
export class TrackLink {
  @ObjectIdColumn()
  id: ObjectID;

  @Column()
  shortened: string;

  @Column()
  remoteAddress: string;

  @Column()
  userAgent: string;

  @CreateDateColumn()
  createdAt: string;

  @UpdateDateColumn()
  updatedAt: string;
}
