import { AutoIncrement, Column, CreatedAt, DataType, Model, PrimaryKey, Table, UpdatedAt } from "sequelize-typescript";

@Table({
    tableName:'convert_url'
})
export default class ConvertUrl extends Model{
    @AutoIncrement
    @PrimaryKey
    @Column(DataType.BIGINT)
    id: number;
    @Column({field:'url_long'})
    urlLong: string;
    @Column({field:'url_long_md5'})
    urlLongMd5: string;
    @Column({field:'url_short'})
    urlShort: string;
    @CreatedAt
    @Column({field:'create_time'})
    createTime: Date;
    @UpdatedAt
    @Column({field:'update_time'})
    updateTime: Date;
    @Column({field:'del_flag'})
    delFlag:number;
}