import { Column, Entity, Index, PrimaryGeneratedColumn } from "typeorm";

/*
 * 1.选用mysql作为存储组件，使用typeorm来访问数据库；
 * 2.为了保持代码简洁，假设整张表只有4个column，id => 自增主键，long_url => 原始的long url，short_url => 生成的short url；
 */
@Entity({
    name: "short_url_info"
})
export class ShortUrlInfo {
    @PrimaryGeneratedColumn()
    id: number; // primary key

    @Index()
    @Column({
        type: "varchar",
        nullable: false,
        length: 256
    })
    long_url: string;   // origin long url string

    @Index()
    @Column({
        type: "varchar",
        nullable: false,
        length: 8
    })
    short_url: string; //  url string
}