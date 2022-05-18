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
| appName     | varchar(64)  |                            |
| authToken   | varchar(32)  |                            |
| createTime  | timestamp    |                            |
| updateTime  | timestamp    |                            |
+-------------+--------------+----------------------------+

 */
@Entity("sl_app")
export class App {
  @PrimaryGeneratedColumn()
  id!: number

  @PrimaryColumn()
  appid!: string

  @Column()
  appName!: string

  @Column()
  authToken!: string

  @CreateDateColumn({ type: "timestamp", comment: "创建时间" })
  createTime!: string

  @UpdateDateColumn({ type: "timestamp", comment: "更新时间" })
  updateTime!: string
}
