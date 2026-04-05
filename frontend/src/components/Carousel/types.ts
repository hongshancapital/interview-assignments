/*
 * @Description: 组件属性定义
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-08 02:40:34
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-12 23:22:26
 */

/**组件通用属性定义 */
export interface BaseCompProps {
  /**子节点*/
  children?: React.ReactNode;
  /**行内样式*/
  style?: React.CSSProperties;
  /**样式名*/
  className?: string;
}

/**轮播组件全局Context属性定义 */
export interface CarouselContextProps {
  /**是否开始轮播 */
  isFlip: boolean;
  /**子元素的个数 */
  itemCount: number;
  /**当前轮播的索引 */
  current?: number;
  /**过渡动画执行时间 */
  animationSecends?: number;
}


/**轮播组件类型定义 */
export interface CarouselProps extends BaseCompProps {
  /**轮播时间间隔 */
  duration?: number;
  /**过渡动画执行时间 */
  animationSecends?: number;
  /**轮播回调事件 */
  onChange: (current?: number) => void;
}

/**轮播组件item类型定义 */
export interface CarouselItemProps extends BaseCompProps {
  /**当前item对应索引值 */
  index?: number;

}

