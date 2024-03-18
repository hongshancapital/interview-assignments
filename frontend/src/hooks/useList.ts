import { useState } from "react";

export type argsProp = number | undefined;

// 最新index的获取hooks
export default function useList<T>(
  items: T[]
): [number, (args: argsProp) => void] {
  const [newIndex, setNewIndex] = useState<number>(0);
  function onNext(args: argsProp) {
    if (args !== undefined) {
      setNewIndex(() => args);
    } else {
      setNewIndex((i) => (i + 1) % items.length);
    }
  }
  return [newIndex, onNext];
}
