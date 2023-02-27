export interface CarouselItemProps {
  key?: string;
  /** 样式前缀 */
  prefixCls?: string;
  /** 当前轮播图标题 */
  title: string;
  /** 当前轮播图描述 */
  description?: string;
  /** 当前轮播图背景图片 */
  backImage?: string;
  /** 当前轮播图背景图片尺寸 */
  backImageStyle?: {
    position?: string | number;
    size?: string | number;
  };
  /** 当前轮播图背景颜色 */
  backColor?: string;
}

export interface CarouselProgressProps {
  /** 样式前缀 */
  prefixCls?: string;
  /** 渲染数量 */
  count?: number;
  /** 当前进度 */
  value?: number;
  /** 等待结束触发事件 */
  onChange?: (value: number) => void;
  /** 等待时间 */
  interval?: number;
}

export interface CarouselProps {
  /** 轮播图样式前缀 */
  prefixCls?: string;
  items: CarouselItemProps[];
  /** 轮播图高度 */
  height?: number | string;
  /** 轮播图宽度 */
  width?: number | string;
  /** 等待时间(ms) */
  interval?: CarouselProgressProps['interval'];
}
