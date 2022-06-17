/*
 * @Author: Zhao Min 曌敏
 * @Date: 2022-06-17 02:33:45
 * @LastEditors: Zhao Min 曌敏
 * @LastEditTime: 2022-06-17 09:01:06
 * @Description: interface for App.ts
 */
import { CarouselContentPropsType } from './components/CarouselContent/interface';

export interface infoItemType extends CarouselContentPropsType {
  readonly customBackgroundColor: string;
  readonly indicatorBackgroundColor: string;
}