import { ShowTimeType } from "./types"

export interface ICarouselProps {
  children: React.ReactNode[] | null // 自定义子元素实例
  duration?: number // 单个page切换动画时长
  showTime?: ShowTimeType // 单个page展示时长
}

// 卡片数据
export interface IItemProps {
  title: string // 标题,
  descList: string[] // 内容，数组可分多行
  bgImgUrl: string
  className?: string
}

