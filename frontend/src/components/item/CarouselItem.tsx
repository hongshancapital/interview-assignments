/*
 * @Description: 轮播图 单项视图
 * @Author: cmh
 * @Date: 2023-03-02 12:01:49
 * @LastEditTime: 2023-03-02 14:42:00
 * @LastEditors: cmh
 */
import React from "react";
import style from "./CarouselItem.module.css";
/**
 * @param {children} children ReactNode
 * @param {width} width 宽度
 * @param {height} height 高度
 * @param {styles} styles 样式
 * @returns 轮播图 单项视图
 */
export const CarouselItem = ({
    children = React.createElement("div"),
    width = "100%",
    height = "100%",
    styles = {},
  }) => {
    return (
      <div
        className={style.carousel_item}
        style={{ width: width, height: height, ...styles }}
      >
        {children}
      </div>
    );
  };