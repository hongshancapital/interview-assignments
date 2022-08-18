import React from "react";
import { FC, ReactNode } from "react";
import styles from '../carousel.module.css'

interface IProps {
  children: ReactNode,
  width?: string
  height?: string
  style?: StyleSheet|object
}
/**
 * 
 * @param children ReactNode
 * @param width 宽度
 * @param height 高度
 * @param styles 样式
 * @returns  轮播图 单项
 */
export const CarouselItem:FC<IProps> =({
  children= React.createElement('div'),
  width='100%',
  height='100%',
  style={}
}) => {
  return (
    <div
    className={styles.carousel_item}
    style={{
      width,
      height,
      ...style
    }}
    >
      {children}
    </div>
  )
}