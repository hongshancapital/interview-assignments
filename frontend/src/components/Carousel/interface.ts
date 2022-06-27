/*
* @Author: Zhao Min 曌敏
* @Date: 2022-06-17 02:37:08
 * @LastEditors: Zhao Min 曌敏
 * @LastEditTime: 2022-06-27 11:15:40
* @Description: 轮播组件
*/

export interface carouselPropsType {
  readonly children: JSX.Element[];
  readonly switchingTime?: number;
  readonly indicatorColorList: Array<String>;
}