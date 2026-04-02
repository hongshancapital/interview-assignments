import {
  useState,
  useRef,
  useEffect,
  useMemo,
  useCallback,
  ReactNode,
  Children,
} from 'react'
import './index.css'

interface CarouselProps {
  duration: number
  speed: number
  children?: ReactNode
}

interface CarouselSlideProps {
  className?: string
  children?: ReactNode
}

function Slide({
  className = '',
  children
}: CarouselSlideProps) {
  return (
    <div className={`carousel-slide ${className}`}>
      {children}
    </div>
  )
}

function Carousel({
  duration,
  speed,
  children
}: CarouselProps) {
  const [activeIndex, setActiveIndex] = useState(0)
  const timerRef = useRef<number>()
  const count = useMemo(() => Children.count(children), [children])

  const run = useCallback(() => {
    clearTimeout(timerRef.current)
    timerRef.current = window.setTimeout(() => {
      setActiveIndex(i => {
        return i + 1 > count - 1 ? 0 : i + 1
      })
      run()
    }, duration)
  }, [count, duration])

  const slideTo = (i: number) => {
    setActiveIndex(i)
    run()
  }

  useEffect(() => {
    run()
    return () => {
      clearTimeout(timerRef.current)
      timerRef.current = undefined
    }
  }, [run])

  return (
    <div className="carousel-container">
      <div
        className="carousel-list"
        style={{
          width: `${100 * count}vw`,
          transition: `transform ${speed}ms linear`,
          transform: `translate3d(-${100 * activeIndex}vw,0,0)`
        }}
      >
        {children}
      </div>
      <div className="carousel-pagination">
        {new Array(count).fill(undefined).map((_, i) => (
          <div
            key={i}
            className="carousel-pagination-item"
            onClick={() => slideTo(i)}
          >
            {activeIndex === i && (
              <span
                className="carousel-pagination-progress"
                style={{
                  animation: `progress ${duration}ms linear`,
                }}
              />
            )}
          </div>
        ))}
      </div>
    </div>
  )
}

Carousel.Slide = Slide

export default Carousel
