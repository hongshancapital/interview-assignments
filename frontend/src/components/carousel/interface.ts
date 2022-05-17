export interface CarouselPros {
  list: Array<any>
  duration?: number
  animationTime?: number
}

export interface ItemProps {
    title: Array<string>  // 主标题
    subTitle?: Array<string> // 副标题
    imgType: 'iphone' | 'airpods' | 'tablet', // 图片类型
    theme: 'black' | 'white' | 'gary', // 主题 展示支持白色和黑色和灰色三种主题
  }


export interface DotProps {
    total: number, // 总数
    active: number, // 当前的面板index
    animationTime: number // 动画的时间(s)
    clickChange?: boolean // 是否点击切换轮播图
}