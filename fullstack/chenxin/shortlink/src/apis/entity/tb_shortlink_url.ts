// shortLink表结构
import { EntityModel } from "@midwayjs/orm";
import {
  Column,
  PrimaryGeneratedColumn,
  CreateDateColumn,
  UpdateDateColumn,
} from "typeorm";

@EntityModel("tb_shortlink_url")
export class ShortLinkUrl {
  @PrimaryGeneratedColumn({
    comment: "主键ID",
  })
  id: number;

  @Column({
    comment: "原始URL",
  })
  url: string;

  @Column({
    name: "short_url",
    comment: "短链接",
  })
  shortUrl: string;

  @Column({
    length: 10,
    comment: "短链状态",
    default: "",
  })
  status: string;

  @UpdateDateColumn({
    // default: () => "NOW()",
    name: "gmt_update",
    type: "timestamp",
    comment: "更新时间",
  })
  gmtUpdate: Date;

  @CreateDateColumn({
    // default: () => "NOW()",
    name: "gmt_create",
    type: "timestamp",
    comment: "创建时间",
  })
  gmtCreate: Date;

  /**
   * 假设存在接入场景则可以增加app_id外键，建立与tb_shortlink_app表主键的多对一对应关系
   * @ManyToOne((type) => ShortLinkApp, (app) => app.urls)
   * @JoinColumn()
   * app: ShortLinkApp;
   * */

  /**
   * 假设只有注册用户才能创建短链则可以增加author_id外键，建立与tb_shortlink_user表的多对一对应关系
   * @ManyToOne((type) => ShortLinkApp, (app) => app.urls)
   * @JoinColumn()
   * author: ShortLinkUser;
   */
}
