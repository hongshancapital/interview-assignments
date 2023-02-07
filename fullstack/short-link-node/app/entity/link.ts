import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  PrimaryColumn,
  Generated,
  CreateDateColumn,
  UpdateDateColumn
} from "typeorm"

/**
 * 
+-------------+--------------+----------------------------+
|                        App                              |
+-------------+--------------+----------------------------+
| id          | int(11)      | PRIMARY KEY AUTO_INCREMENT |
| appid       | varchar(32)  | PRIMARY HASH INDEX         |
| slink       | varchar(32)  | PRIMARY HASH INDEX         |
| oriUrl      | varchar(256) |                            |
| oriUrl      | tinyint(4)   |                            |
| createTime  | timestamp    |                            |
| updateTime  | timestamp    |                            |
+-------------+--------------+----------------------------+

 */
@Entity("sl_slink")
export class Link {
  @PrimaryGeneratedColumn()
  id!: number

  @PrimaryColumn()
  slink!: string

  @Column()
  appid!: string

  @Column({ name: "ori_url" })
  oriUrl!: string

  @Column({ name: "is_delete" })
  isDelete!: number

  @CreateDateColumn({ type: "timestamp", comment: "创建时间", name: "create_time" })
  createTime!: string

  @UpdateDateColumn({ type: "timestamp", comment: "更新时间", name: "update_time" })
  updateTime!: string
}
