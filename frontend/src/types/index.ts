/**
 * Crousel数据模型
 */
export interface DataItem {
  /**
   * unique id
   */
  id: number;
  /**
   * 标题
   */
  title: string[];
  /**
   * 副标题
   */
  subTitle?: string[];
  /**
   * 字体颜色
   */
  color: string;
  /**
   * 背景颜色
   */
  backgroundColor: string;
  /**
   * 图片
   */
  img: string;
}
