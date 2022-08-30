export type CarouselItemProps = {
  /** 标题文本, 支持使用 '\n' 换行符. */
  title: string

  /** 描述文本, 支持使用 '\n' 换行符. */
  desc?: string

  /** 背景图片, 相对 src/assets 路径, e.g: /airpods.png */
  bgImg: string

  /** 背景颜色. */
  bgColor: string

  /** 是否当前活跃项. */
  active?: boolean

  /** 轮播项所处位置下标. */
  idx?: number
}

export type CarouselProps = {
  /** 是否自动播放, 默认: true. */
  autoPlay?: boolean

  /** 每一页停留时间, 单位: 毫秒, 默认: 3000. */
  duration?: number

  /** 是否显示每一页播放进度, 默认: true. */
  showProgress?: boolean

  /** 走马灯细项. */
  items?: Array<CarouselItemProps>
}
