/** 走马灯动画配置 */
export interface AnimationConfig {
  /** 走马灯动画间隔，单位毫秒 */
  duration: number;
  /** 走马灯单个动画执行时间，单位毫秒 */
  transitionDuration: number;
}

/** 文本描述配置 */
export interface DescContentConfig {
  /** 文本id标识 */
  id: string;
  /** 文本内容 */
  text: string;
  /** 文本样式 */
  style: TextStyleConfig;
}

/** 文本样式配置 */
export interface TextStyleConfig {
  /** 字体大小 */
  fontSize: string;
  /** 文字行高 */
  lineHeight: string;
  /** 字体高度 */
  fontWeight: string;
  /** 文字颜色 */
  color: string;
  /** 组件顶部外边距 */
  marginTop?: string;
}
