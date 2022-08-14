import React, { ComponentType, FC, ReactElement, useMemo } from 'react';

/** 简单处理多个class的工具函数 */
export function cx(...cls: (string | undefined | boolean)[]) {
  const filteredCls = cls.filter(Boolean);
  return filteredCls.length ? filteredCls.join(' ') : '';
}

/** 获取屏幕的尺寸 */
export function useScreen() {
  return useMemo(() => {
    return [window.innerWidth, window.innerHeight];
  }, []);
}