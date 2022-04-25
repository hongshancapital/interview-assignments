/*
 * @Author: 马俊鸿 zxmajunhong@forxmail.com
 * @Date: 2022-04-24 20:54:09
 * @LastEditTime: 2022-04-25 16:29:39
 * @Description: Carousel组件的类型申明文件
 */

/** Carousel所需要的props类型 */
type TCarouselProps = {
  /** 所需要的轮播列表数据 */
  datas: {
    /** 大标题内容 */
    title: string;
    /** 子标题内容 */
    subTitle: string;
    /** 背景图片地址 可选以@开头的相对地址或者绝对地址 */
    bg: string | any;
    /** 文字的颜色 */
    color?: string;
  }[];
  /** 切换速度 毫秒为单位 默认为3000 */
  speed?: number;
  /** TODO 是否无缝滚动 */
  loop?: boolean;
};
