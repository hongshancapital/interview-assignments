import dayjs = require("dayjs")
import { Entity, PrimaryGeneratedColumn, Column, Index } from "typeorm"

@Entity()
export class ShortUrlMap {
	@PrimaryGeneratedColumn()
	id: number

	@Column({
		type: "varchar",
	})
	shortUrl: string

	@Index()
	@Column({
		type: "text",
	})
	longUrl: string

	@Index()
	@Column()
	shortHash: string

	@Column({
		default: () => "CURRENT_TIMESTAMP",
		type: "timestamp",
	})
	createTime: Date

	// 短链接失效时间
	@Column({
		type: "timestamp",
	})
	expireTime: Date
}
