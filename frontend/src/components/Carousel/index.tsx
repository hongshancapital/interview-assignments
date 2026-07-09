import { FunctionComponent, PropsWithChildren, useCallback, useEffect, useState } from 'react'
import classnames from 'classnames'
import styles from './index.module.scss'

export type CarouselProps = {
  duration?: number
  className?: string
}

const Carousel: FunctionComponent<PropsWithChildren<CarouselProps>> = ({
  className,
  children,
  duration = 2000
}) => {
  const [currentIndex, setCurrentIndex] = useState(0) // 当前滚动index
  const [started, setStarted] = useState(false) // 告诉程序是否开始滚动了

  const carouselList = children as JSX.Element[]
  const carouselLength = carouselList.length

  const changeCurrentIndex = useCallback(() => {
    const nextIndex = currentIndex >= carouselLength - 1 ? 0 : currentIndex + 1
    setCurrentIndex(nextIndex)
  }, [carouselLength, currentIndex])

  useEffect(() => {
    setStarted(true)
  }, [])

  useEffect(() => {
    const timer = setTimeout(changeCurrentIndex, duration)
    return () => clearTimeout(timer)
  }, [duration, changeCurrentIndex])

  const getCarouselItemStyle = (index: number) => {
    const itemIndex = index - currentIndex
    return {
      transform: `translate3d(${itemIndex * 100}%, 0px, 0px)`,
      msTransform: `translate3d(${itemIndex * 100}%, 0px, 0px)`,
      WebkitTransform: `translate3d(${itemIndex * 100}%, 0px, 0px)`,
      transition: `transform ${duration / 1000}s ease`
    }
  }

  const getNavigationItemStyle = (index: number) => {
    const isActive = started && index === currentIndex
    return isActive
      ? { width: '100%', transition: `width ${duration / 1000}s ease` }
      : { width: 0, transition: 'no' }
  }

  const createCarouselList = () => {
    return carouselList.map((carouselItem: JSX.Element, index: number) => {
      return (
        <div
          className={styles['carousel-item']}
          style={getCarouselItemStyle(index)}
          key={carouselItem.key || index}
        >
          {carouselItem}
        </div>
      )
    })
  }

  const createNavigation = () => {
    return (
      <ul className={styles[`carousel-navigation`]}>
        {carouselList.map((carouselItem: JSX.Element, index: number) => (
          <li
            className={classnames(styles[`carousel-navigation-item`])}
            key={carouselItem.key || index}
          >
            <span
              className={styles[`carousel-navigation-item-inner`]}
              style={getNavigationItemStyle(index)}
            />
          </li>
        ))}
      </ul>
    )
  }

  return (
    <div className={classnames(styles.carousel, className)}>
      {createCarouselList()}
      {createNavigation()}
    </div>
  )
}

export default Carousel
