/**
 * @export
 * @interface IData
 * @param imgUrl 图标url
 * @param color 文字颜色
 * @param bgColor 背景颜色
 * @param title 标题
 * @param description 描述
 * @param price 价格描述
 */
export interface IData {
  imgUrl: string
  color: string
  bgColor:string
  title: string
  description?: string
  price?: string
}
