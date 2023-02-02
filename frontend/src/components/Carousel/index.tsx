import {
  useEffect,
  useRef,
  useState,
  Children,
  ReactNode,
  CSSProperties,
  useMemo,
  forwardRef,
  useImperativeHandle
} from 'react'

import styles from './index.module.scss'

const DEFAULT_SPEED = 1500
const DEFAULT_SIZE = 400

export interface CarouselSetting {
  speed?: number
  autoplay?: boolean
  loop?: boolean
  children: ReactNode
}

export interface CarouselRef {
  goTo: (slide: number) => void
  next: () => void
  prev: () => void
}

const Carousel = forwardRef<CarouselRef, CarouselSetting>(
  ({ speed = DEFAULT_SPEED, autoplay = true, loop = true, children }, ref) => {
    const [activeIndex, setActiveIndex] = useState<number>(0)
    const [slideStyle, setSlideStyle] = useState<CSSProperties>({
      width: '100%',
      height: `${DEFAULT_SIZE}px`
    })
    const slideContainerRef = useRef<HTMLDivElement>(null)
    const timer = useRef<ReturnType<typeof setInterval>>()

    const play = () => {
      timer.current = setInterval(() => {
        setActiveIndex(prev => prev + 1)
      }, speed)
    }

    const slideTo = (index: number) => {
      setActiveIndex(index)

      // cancel timer when click pagination item
      if (timer.current) {
        clearInterval(timer.current)
        timer.current = undefined
      }
    }

    const childrenArray = useMemo(() => {
      return Children.toArray(children).filter(child => {
        if (typeof child === 'string') {
          return !!child.trim()
        }
        return !!child
      })
    }, [children])

    useImperativeHandle(ref, () => ({
      goTo(slide) {
        slideTo(slide)
      },
      prev() {
        setActiveIndex(prev => prev + 1)
      },
      next() {
        if (activeIndex === 0) {
          setActiveIndex(childrenArray.length - 1)
        } else {
          setActiveIndex(prev => prev - 1)
        }
      }
    }))

    useEffect(() => {
      if (slideContainerRef.current) {
        autoplay && play()
      }
      return () => {
        clearInterval(timer.current)
        timer.current = undefined
      }
    }, [autoplay])

    useEffect(() => {
      // container's width inherits parent container
      const childrenArray = Children.toArray(children)
      if (!childrenArray.length) return
      const firstChild = childrenArray[0] as JSX.Element

      const currentHeight = firstChild.props?.style?.height || DEFAULT_SIZE
      setSlideStyle({
        width: '100%',
        height: currentHeight
      })
    }, [children])

    useEffect(() => {
      if (slideContainerRef.current) {
        const width = slideContainerRef.current.getBoundingClientRect().width
        slideContainerRef.current.style.right = `${activeIndex * +width}px`
      }

      // check boundary condition
      if (activeIndex === childrenArray.length) {
        if (loop) {
          setActiveIndex(0)
        } else {
          clearInterval(timer.current)
          timer.current = undefined
        }
      }
    }, [activeIndex, slideStyle, childrenArray, loop])

    const slideContent = []
    for (let i = 0; i < childrenArray.length; i++) {
      slideContent.push(
        <div className={styles.slide} key={i}>
          {childrenArray[i]}
        </div>
      )
    }

    return (
      <div className={styles.wrapper} data-testid='carousel'>
        <div className={styles.content}>
          <div className={styles.slides} style={slideStyle}>
            <div className={styles.slides__content} ref={slideContainerRef}>
              {slideContent}
            </div>
          </div>

          <div className={styles.pagination} style={{ width: slideStyle.width }}>
            {childrenArray.map((child: ReactNode, index: number) => (
              <div
                className={styles.pagination__item}
                key={index}
                data-testid='pagination-item'
                onClick={() => slideTo(index)}
              >
                {index === activeIndex && (
                  <div
                    className={styles.pagination__inner}
                    style={{ animationDuration: `${speed / 1000}s` }}
                  />
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    )
  }
)

export default Carousel
