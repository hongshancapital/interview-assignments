import { useState } from "react"

export default function usePage(defaultPageIndex = 0, len = 1) {
  // 当前页码
  const [pageIndex, setPageIndex] = useState(defaultPageIndex);
  const [length] = useState(len - 1);

  // 一次性往前（或往后）跳几页
  const jumpPage = (page: number) => {
    if (page > length) {
      setPageIndex(0);
    } else if (page > -1) {
      setPageIndex(page);
    } else {
      setPageIndex(0);
    }
  }

  // 上一页
  const prevPage = () => jumpPage(pageIndex - 1);

  // 下一页
  const nextPage = () => jumpPage(pageIndex + 1);

  return { pageIndex, length, jumpPage, prevPage, nextPage }
}