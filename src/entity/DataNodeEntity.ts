import {Entity, Column, PrimaryGeneratedColumn} from "typeorm";
@Entity({name:"data_node"})
class DataNodeEntity {
    
    @PrimaryGeneratedColumn({name:"id"})
    id: number;

    @Column({name:"slot_name"})
    slotName:string;

    @Column({name:"origin"})
    origin:string;

    @Column({name:"code"})
    code:string;

    @Column({name:"md5"})
    md5:string;
    
}

export {DataNodeEntity}