import React, {
  FC,
  cloneElement,
  useEffect,
  useState,
  Children,
  useMemo,
  useRef,
} from "react";
import classNames from 'classnames'
import style from "./index.module.scss"

interface CarouselProps {
  duration?: number;
}


const Carousel: FC<CarouselProps> = (props) => {
  const { duration = 4000, children } = props
  const [activeIndex, setActiveIndex] = useState(-1)
  const ref = useRef(-1)

  const newchildren = useMemo(() => {
    return Children.toArray(children).filter(child => {
      if (typeof child === "string") {
        return !!child.trim()
      }
      return !!child
    })
  }, [children])

  const slideCount = Children.count(newchildren)

  useEffect(() => {
    ref.current = 0
    setActiveIndex(ref.current)
    const timer = setInterval(() => {
      const nextIndex = ref.current + 1
      ref.current = nextIndex >= slideCount ? 0 : nextIndex
      setActiveIndex(ref.current)
    }, duration)
    return () => clearInterval(timer)
  }, [duration, slideCount])

  return (
    <div className={style.container}>
      <div
        className={style.content}
        style={{ width: `${newchildren.length}00%`, left: `-${activeIndex}00%` }}
      >
        {newchildren.map((v: any, index) =>
          cloneElement(v, {
            ...v.props,
            className: classNames(style.item, v.props?.className),
            key: index
          })
        )}
      </div>
      <div className={style.dots}>
        {newchildren.map((item, index) => {
          const isActive = index === activeIndex;
          return (
            <div
              key={index}
              className={style.dot}
            >
              <div className={style.progress}
                style={{
                  width: isActive ? "100%" : 0,
                  transition: isActive
                    ? `${duration / 1000}s width`
                    : "",
                }}>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Carousel