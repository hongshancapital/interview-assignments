import {RefObject, useRef} from "react";
import useSizeEffect from "../useSizeEffect";

interface Size {
  width: number;
  height: number;
}

const defaultSize = {
  width: 0,
  height: 0
}

export default function useSize<T extends unknown = HTMLDivElement>(elementRef: RefObject<T>) {
  const ref = useRef<Size>(defaultSize);

  useSizeEffect(() => {
    if (elementRef.current) {
      const { width, height } = (elementRef as RefObject<HTMLDivElement>).current?.getBoundingClientRect() ?? {} as Size;
      ref.current = {
        width,
        height
      }
    }

  }, []);
  return ref;
}