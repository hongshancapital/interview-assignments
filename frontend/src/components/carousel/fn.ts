import { useRef } from "react";

export default function usePage(defaultIndex: number) {
  // 当前页码
  const pageIndex = useRef(defaultIndex);
  
  return pageIndex;
}
