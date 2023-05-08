// 声明banner数据类型
export interface BannerProps {
  image?: any
  element?: React.ReactNode
}
// 声明父级数据类型
export interface IProps {
  width?: number|string // 区域宽度
  height?: number|string // 区域高度
  options?: Array<BannerProps> // banner数据
  number?: number // 显示banner数据
  index?: number // 显示第几个图
  isIcon?: boolean // 是否显示底部图标
  className?: string
  time?: string|number // 时间设置单位为秒
  footer?: {
    bottom?: number|string // 距离底部距离
    align?: string // 所在位置，center居中，left居左，right居右
    paddingLeft?: number|string
    paddingRight?: number|string
    className?: string
  }
  direction?: string // 方向, left和right
}

// 声明Provider数据类型
export interface ContextProps {
  children: React.ReactElement
  value?: IProps
}

// 声明dispatch中数据参数
export interface ActionProps {
  type: string
  payload: IProps
}

export const initValue: IProps = {
  index: 0,
  isIcon: true,
  number: 0,
  direction: 'left',
  footer: {
    bottom: '15px'
  }
}