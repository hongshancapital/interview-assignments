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
+-------------+--------------+----------------------------+

 */
@Entity("sl_link_shadow")
export class LinkShadow {
  @PrimaryGeneratedColumn()
  id!: number
}
