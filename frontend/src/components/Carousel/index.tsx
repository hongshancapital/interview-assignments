import {
  HTMLProps,
  ReactNode,
  useEffect,
  useMemo,
  useRef,
  useState,
} from 'react'
import classNames from 'classnames'

import { Indicator } from './Indicator'

import './styles.scss'

export interface CarouselProps<T>
  extends Omit<HTMLProps<HTMLDivElement>, 'data'> {
  autoPlay?: boolean
  duration?: number
  data: T[]
  renderItem: (item: T, index: number) => ReactNode
  keyExtractor?: (item: T, index: number) => string | number
}

export default function Carousel<T>({
  autoPlay = true,
  duration = 3000,
  data,
  renderItem,
  keyExtractor = (_, index) => index,
  className,
  ...restProps
}: CarouselProps<T>) {
  const [active, setActive] = useState(0)
  const timerRef = useRef<ReturnType<typeof setInterval>>()

  useEffect(() => {
    if (autoPlay && data.length > 1) {
      timerRef.current = setInterval(() => {
        setActive((prev) => {
          return prev === data.length - 1 ? 0 : prev + 1
        })
      }, duration)

      return () => clearInterval(timerRef.current)
    }
  }, [autoPlay, data.length, duration])

  const translateX = useMemo(() => {
    return -(active / data.length) * 100 + '%'
  }, [active, data.length])

  return (
    <div className={classNames('carousel-wrapper', className)} {...restProps}>
      <ul
        className='carousel-list'
        style={{
          width: `${data.length * 100}%`,
          transform: `translate3d(${translateX}, 0, 0)`,
        }}
      >
        {data.map((item, index) => {
          return (
            <li
              key={keyExtractor(item, index)}
              className='carousel-item'
              style={{ width: `${(1 / data.length) * 100}%` }}
            >
              {renderItem(item, index)}
            </li>
          )
        })}
      </ul>
      <Indicator count={data.length} active={active} duration={duration} />
    </div>
  )
}
