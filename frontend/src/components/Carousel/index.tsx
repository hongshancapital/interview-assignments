import React, { useState, useEffect, useRef } from 'react'
import { css } from '@emotion/css'
import { keyframes } from '@emotion/react'

const slidePrev = keyframes`
  from {
    opacity: 1;
    transform: translateX(0);
  }
  to {
    opacity: 1;
    transform: translateX(-100%);
  }
`

const slideNext = keyframes`
  from {
    opacity: 1;
    transform: translateX(100%);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
`

const btnBg = keyframes`
  0% {
    width: 0;
  }
  100% {
    width: 100%;
  }
`

const getStyles = (delay: number, duration: number) => {
  return {
    carouselContainer: css`
      position: relative;
      width: 100%;
      height: 100%;
    `,
    slide: css`
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      opacity: 0;
      box-sizing: border-box;
      overflow: hidden;
    `,
    prev: css`
      animation-name: ${slidePrev};
      animation-duration: ${duration}ms;
    `,
    next: css`
      animation-name: ${slideNext};
      animation-duration: ${duration}ms;
      opacity: 1;
    `,
    tool: css`
      position: absolute;
      width: 100%;
      bottom: 30px;
      display: flex;
      justify-content: center;
    `,
    btn: css`
      height: 4px;
      width: 50px;
      border-radius: 4px;
      margin: 0 2px;
      background-color: #ccc;
    `,
    btnActive: css`
      &::before {
        content: '';
        display: block;
        height: 4px;
        width: 0;
        background: #fff;
        animation-name: ${btnBg};
        animation-duration: ${delay}ms;
      }
    `
  }
}

interface Props {
  children: React.ReactNode
  delay?: number
  duration?: number
}

const Carousel: React.FC<Props> = ({
  children,
  delay = 4000,
  duration = 1000
}) => {
  const styles = getStyles(delay, duration)
  const slides: any = React.Children.map(children, (child, index) => ({
    id: index,
    content: child
  }))

  const [currentSlide, setCurrentSlide] = useState<number>(0)
  const [prevSlide, setPrevSlide] = useState<number>(1)

  const timeoutRef = useRef<NodeJS.Timeout | null>(null)

  useEffect(() => {
    timeoutRef.current = setTimeout(() => {
      setCurrentSlide((currentSlide + 1) % slides.length)
      setPrevSlide((prevSlide + 1) % slides.length)
    }, delay)

    return () => {
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current)
      }
    }
  }, [currentSlide, prevSlide, delay, slides.length])

  return (
    <div className={styles.carouselContainer}>
      {slides.map((slide: any) => (
        <div
          key={slide.id}
          className={`${styles.slide} ${
            slide.id === currentSlide ? styles.prev : ''
          } ${slide.id === prevSlide ? styles.next : ''}`}
        >
          {slide.content}
        </div>
      ))}
      <div className={styles.tool}>
        {slides.map((slide: any) => (
          <div
            key={slide.id}
            className={`${styles.btn} ${
              slide.id === prevSlide ? styles.btnActive : ''
            }`}
          ></div>
        ))}
      </div>
    </div>
  )
}

export default Carousel
