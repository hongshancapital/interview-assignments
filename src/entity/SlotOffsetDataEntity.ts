import {Entity, Column, PrimaryColumn} from "typeorm";

@Entity({name:"slot_offset_data"})
class SlotOffsetDataEntity{
    @PrimaryColumn({name:"slot_name"})
    slotName:string;

    @Column({name:"current_offset"})
    currentOffset:number;
}

export {SlotOffsetDataEntity}