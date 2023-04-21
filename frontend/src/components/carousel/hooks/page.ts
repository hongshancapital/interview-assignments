import { useCallback, useMemo, useState } from "react"

export const usePage = (length = 0) => {
  const [currentPage, setCurrentPage] = useState(0)

  // 是否切换到了carouselData.length + 1张
  const isSpliceModule = useMemo(() => (
    currentPage === length
  ), [currentPage, length])

  // 跳转到指定页码
  const toPage = useCallback((page: number) => {
    if (page > length) {
      setCurrentPage(0)
    } else if (page >= 0) {
      setCurrentPage(page)
    } else {
      setCurrentPage(0)
    }
  }, [length])

  // 切换下一页
  const nextPage = useCallback(() => toPage(currentPage + 1), [currentPage])

  // 当前indicator是否为激活状态
  const isActive = useCallback((index: number) => {
    /**
     * 实现最后一张继续向右轮播的辅助方法
     * 当前在第 carouselData.length + 1张时，tab第一个按钮为激活状态
     */
    const isSpliceActive = isSpliceModule && index === 0
    const isActiveIndicator = index === currentPage

    return isActiveIndicator || isSpliceActive
  }, [currentPage, isSpliceModule])

  return { currentPage, setCurrentPage, nextPage, toPage, isActive }
}
