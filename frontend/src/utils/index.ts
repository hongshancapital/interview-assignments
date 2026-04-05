/*
 * @Description: 通用工具函数
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-08 00:51:00
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-09 23:57:30
 */

import React, { isValidElement } from "react";
/**
 * @description: 获取有效的子组件
 * @param {React} children   子组件
 * @param {string} displayName  组件静态displayName属性(判断组件是属于哪一类型组件)
 * @param {*} extendProps 扩展属性
 * @return {*}
 * @author: zhangwei
 */
export const getValidChildNodes= (children: any, displayName: string, extendProps = {}) => {
  let childNodes: React.ReactElement[] = [];
  React.Children.map(children, (childItem:{type:any,displayName:string,props:any}, index) => {
    if (isValidElement(childItem)) {
      const { type, props } = childItem;
      if (type.displayName === displayName) {
        childNodes.push(
          React.cloneElement(childItem, { ...props, index ,...extendProps})
        );
      }
    }
  })
  return childNodes;
};
 