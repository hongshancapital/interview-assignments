import { Entity, Column, PrimaryGeneratedColumn, Index } from 'typeorm'

@Entity('short_url_mapping')
export class ShortUrlMapping {
    // 主键id
    @PrimaryGeneratedColumn()
      id: number
      
    // 短码，建立索引
    @Index()
    @Column({
      name: 'short_url_code',
      length: 8,
    })
      shortUrlCode: string

    // 原链接
    @Column({
      name: 'original_url',
      length: 2083,
    })
      originalUrl: string 

    // 原链接hash码，建立索引
    @Index()
    @Column({
      name: 'original_url_hash',
      length: 32,
    })
      originalUrlHash: string

    @Column({
      name: 'create_time',
      type: 'timestamp', default: () => 'CURRENT_TIMESTAMP',
    })
      createTime: Date

    // 预留字段
    @Column({
      name: 'create_user_id',
    })
      createUserID: string
}
