import {DependencyList, useCallback} from "react";

function debounce<T extends unknown[]>(func: (...args: T) => void, delay: number) {
  let timerId: NodeJS.Timer;

  return function (...args: T) {
    timerId && clearTimeout(timerId);
    timerId = setTimeout(() => {
      // @ts-ignore
      func.apply(this, args);
    }, delay);
  };
}

export default function useDebounceCallback<T extends unknown[]>(callback: (...args: T) => void, deeps: DependencyList, delay: number) {
  return useCallback(debounce(callback, delay), deeps);
}