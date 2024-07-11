import { useEffect, useState } from "react";

export interface Options {
  onEnter?: () => void;
  onLeave?: () => void;
}

/**
 * @description: 检测dom元素是否处于hover
 * @param {HTMLElement | null} target 目标dom元素
 * @param {Options} options onEnter onLeave 鼠标移入移出时触发的事件
 * @return {boolean} state 是否处于hover状态
 */
const useHover = (target?: HTMLElement | null, options?: Options): boolean => {
  const { onEnter, onLeave } = options || {};

  const [state, setState] = useState(false);

  useEffect(() => {
    if (!target) {
      return;
    }
    const enterListener = () => {
      onEnter?.();
      setState(true);
    };
    target.addEventListener("mouseenter", enterListener);
    return () => {
      target.removeEventListener("mouseenter", enterListener);
    };
  }, [target, onEnter]);

  useEffect(() => {
    if (!target) {
      return;
    }
    const leaveListener = () => {
      onLeave?.();
      setState(false);
    };
    target.addEventListener("mouseleave", leaveListener);
    return () => {
      target.removeEventListener("mouseleave", leaveListener);
    };
  }, [target, onLeave]);

  return state;
};

export default useHover;
