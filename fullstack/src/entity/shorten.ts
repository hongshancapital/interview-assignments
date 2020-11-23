import {
	Entity,
	Column,
	ObjectID,
	ObjectIdColumn,
	CreateDateColumn,
	UpdateDateColumn,
} from 'typeorm';

@Entity()
export class Shorten {
	@ObjectIdColumn()
	id: ObjectID;

	@Column()
	link: string;

	@Column()
	shortened: string;

	@CreateDateColumn()
	createdAt: string;

	@UpdateDateColumn()
	updatedAt: string;
}
