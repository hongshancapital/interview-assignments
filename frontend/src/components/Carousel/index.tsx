import React, {
  FC,
  HTMLAttributes,
  ReactElement,
  useLayoutEffect,
  useMemo,
  useRef,
  useState,
} from 'react'
import classnames from 'classnames'
import './index.scss'
import Dots from './Dots'

export interface ICarouselProps extends HTMLAttributes<HTMLDivElement> {
  autoplay?: boolean //是否自动播放
  autoPlayInterval?: number //自动播放间隔
  dots?: boolean //是否显示dots
  infinity?: boolean //Todo 视频中展示的是回头滚动，不是同方向滚动
  children: ReactElement[]
}
const Carousel: FC<ICarouselProps> = (props) => {
  const {
    autoplay,
    autoPlayInterval,
    dots,
    children,
    className,
    ...restProps
  } = props
  const childrenLength = useMemo(() => children.length, [children])
  const [index, setIndex] = useState<number>(0)
  const timer = useRef<NodeJS.Timeout>()
  useLayoutEffect(() => {
    if (!autoplay) return
    timer.current = setTimeout(() => {
      setIndex((i) => (i + 1) % childrenLength)
    }, autoPlayInterval)
    return () => {
      timer.current && clearTimeout(timer.current)
    }
  }, [index, autoPlayInterval, autoplay, childrenLength])
  const classes = useMemo(
    () => classnames('com-carousel-wrap', className),
    [className],
  )
  return (
    <div className={classes} {...restProps}>
      <div className={classnames('com-carousel', `com-offset${index}`)}>
        {React.Children.map(children, (child: ReactElement) => {
          return (
            <child.type
              {...child.props}
              className={classnames(
                child.props?.className,
                'com-carousel-item-box',
              )}
            />
          )
        })}
      </div>
      {dots ? (
        <Dots
          count={childrenLength}
          current={index}
          autoPlayInterval={autoPlayInterval}
        />
      ) : null}
    </div>
  )
}

Carousel.defaultProps = {
  autoplay: true,
  autoPlayInterval: 2000,
  dots: true,
}
export default Carousel
