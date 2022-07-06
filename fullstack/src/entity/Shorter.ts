import {
    Entity,
    PrimaryGeneratedColumn,
    Column,
    CreateDateColumn,
    UpdateDateColumn
} from "typeorm"

@Entity()
export class Shorter {

    @PrimaryGeneratedColumn()
    id: number

    @Column()
    origin: string // link origin

    @Column()
    path: string // link split origin

    @Column({ unique: true })
    shorter: string
    
    @CreateDateColumn() // timestamp if needed.
    created_at: Date;

    @UpdateDateColumn()
    updated_at: Date;

}
