import {Model, Table, Column, PrimaryKey, DataType, UpdatedAt, Default} from 'sequelize-typescript';

export interface SegmentsAttributes {
    app_tag: string;
    max_id: bigint;
    step: bigint;
    update_time: Date;
}

@Table({
    tableName: 'segments',
    timestamps: false,
    freezeTableName: true,
})
export class Segments extends Model<SegmentsAttributes> {
    @PrimaryKey
    @Column({
        type: DataType.STRING,
        comment: '',
    })
    app_tag!: string;

    @Default(0n)
    @Column
    max_id!: bigint;

    @Default(10000n)
    @Column
    step!: bigint;

    @UpdatedAt
    @Column
    update_time!: Date;
}
