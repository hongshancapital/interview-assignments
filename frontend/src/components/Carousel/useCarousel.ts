import { useRef, useState } from 'react'

type IParams = {
  /* 子级个数 */
  count: number
  /* 动画过渡时间 */
  duration: number
  /* 某一项的宽度 */
  width: number
}


const setStyle = (
  dom: HTMLDivElement | null,
  options: { offset: number, duration: number}
) => {
  if(!dom) {
    return
  }
  const { offset } = options
  dom.style.transition = `transform ${options.duration}ms`
  dom.style.transform = `translateX(${offset}px)`
}

const useCarousel = ({count, duration, width}: IParams) => {
  // 当前索引
  const [current, setCurrent] = useState(0)
  const scrollerRef = useRef<HTMLDivElement>(null)

  const goTo = (index: number) => {
    if(count <= 1) {
      return
    }
    if(index < 0 || index >= count) {
      console.warn('index out of range');
      return
    }
    setCurrent(index);
    setStyle(scrollerRef.current, {offset: -index * width, duration})
  }

  const step = (direction: number) => {
    const index = (current + direction) % count
    goTo(index)
  }

  const next = () => {
    step(1)
  }

  const prev = () => {
    step(-1)
  }

  return {
    scrollerRef,
    current,
    goTo,
    next,
    prev
  }
}

export default useCarousel