import React from "react"

export interface IImageItem {
  /** @description 图片地址 */
  img: string
  /** @description 标题 */
  title?: string
  /** @description 描述 */
  description?: string
  /** @description 字体颜色 */
  textColor?: string
}

export interface ICarouseItemProps {
  data: IImageItem
}

export interface ICarouseProps {
  children?: React.ReactNode[];
  /** @description 是否展示底部的指示符 */
  showIndicator?: boolean
  /** @description 自动切换时长 */
  duration?: number
  /** @description 自定义box的样式 */
  boxStyle?: React.CSSProperties
}