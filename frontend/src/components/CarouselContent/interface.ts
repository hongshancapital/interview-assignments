/*
 * @Author: Zhao Min 曌敏
 * @Date: 2022-06-17 00:19:06
 * @LastEditors: Zhao Min 曌敏
 * @LastEditTime: 2022-06-17 02:54:25
 * @Description: 每个轮播图子项的可业务定制化内容
 */
export interface CarouselContentPropsType {
  readonly title?: string;
  readonly description?: string;
  readonly imgUrl?: string;
  readonly fontColor?: string;
}