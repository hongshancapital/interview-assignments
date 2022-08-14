import React, { ComponentType, FC, ReactElement, useEffect, useMemo, useRef } from 'react';

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

export function useUpdate(fn: Function, deps: ReadonlyArray<any>) {
  const ref = useRef(false);
  useEffect(() => {
    if (!ref.current) ref.current = true;
    else fn();
  }, deps);
}
