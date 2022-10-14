/** 业务数据模型层（可以对应后台cig接口的响应数据类型） */
import { AnimationConfig, DescContentConfig } from './config';

/** 走马灯数据类型 */
export interface CarouselData {
  /** 滑动内容块 */
  slideItems: SlideItem[];
  /** 动画配置 */
  animationConfig: AnimationConfig;
}

/** 滑动内容块数据类型 */
export interface SlideItem {
  /** 滑块id */
  slideId: number;
  /** 滑块区域背景色 */
  bgColor: string;
  /** 图片url */
  imgUrl: string;
  /** 文本描述配置 */
  descContents: DescContentConfig[];
}
