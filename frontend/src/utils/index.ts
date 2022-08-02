import React, { ReactElement, JSXElementConstructor } from 'react'

/**
 * 校验子容器是否合法
 * @param children
 * @param targetElement 合法的目标容器
 * @param errMessage 自定义错误信息
 * @returns 合法的子容器数组
 */
export const validateAndGetChildren = (prams: {
  children: ReactElement | ReactElement[]
  targetElement: string | JSXElementConstructor<any>
  errMsg: string
}) =>
  React.Children.map(prams.children, child => {
    if (!React.isValidElement(child)) return null
    if (child.type !== prams.targetElement) {
      // 抛出开发级别错误，直接阻断运行
      throw new Error(prams.errMsg)
    }
    return child
  })
