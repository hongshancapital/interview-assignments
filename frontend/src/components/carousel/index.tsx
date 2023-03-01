import React from 'react';
import classNames from 'classnames'
import { CarouselProps, CarouselRef, AnimationState } from './interface';
import './index.scss'

const Carousel = React.forwardRef<CarouselRef, CarouselProps>(
  (
    {
      autoplay = true,
      defaultIndex = 0,
      dots = true,
      dotPosition = 'bottom',
      duration = 3000,
      className,
      beforeChange,
      afterChange,
      ...props
    },
    ref
  ) => {
    // 逻辑部分
    const children = React.Children.toArray(props.children)
    const childrenCount = React.useRef(children.length)
    const getSafeIndex = (idx: number) => (idx % childrenCount.current) || 0

    const [currentIndex, setCurrentIndex] = React.useState(getSafeIndex(defaultIndex))
    const [playAnimation, setPlayAnimation] = React.useState(true)
    const [playState, setPlayState] = React.useState<AnimationState>('running')

    const goTo = (index: number, animation: boolean = true) => {
      if (index === currentIndex) return

      if (beforeChange && beforeChange(currentIndex, index) === false) return
      const idx = getSafeIndex(index)
      setCurrentIndex(idx)
      afterChange && afterChange(idx)

      if (animation !== playAnimation) setPlayAnimation(animation)
    }
    const next = () => goTo(currentIndex + 1)
    const prev = () => goTo(currentIndex - 1)

    React.useImperativeHandle(ref, () => ({
      goTo,
      next,
      prev,
    }))
    React.useEffect(() => {
      if (playAnimation === false) {
        setPlayAnimation(true)
      }
    }, [playAnimation])

    // Dom 部分
    const rootContainerStyle = {
      width: `${childrenCount.current * 100}%`,
      transitionDuration: playAnimation ? '0.5s' : '0s',
      transform: `translate3D(-${(100 * currentIndex) / childrenCount.current}%, 0, 0)`,
    }

    const hasNext = autoplay && duration !== 0
    const enableDots = !!dots
    const dtsClass = classNames([
      'dots',
      `dots-${dotPosition}`,
      typeof dots === 'boolean' ? '' : dots?.className
    ])
    const dtsStyle = (idx: number): React.CSSProperties => {
      if (currentIndex === idx) {
        const isX = ['top', 'bottom'].includes(dotPosition)
        const direction = isX ? 'width' : 'height'
        const animationName = isX ? 'progress-width' : 'progress-height'
        return {
          [direction]: hasNext ? 'auto' : '100%',
          animation: `${animationName} ${hasNext ? duration : 0}ms linear`,
          animationPlayState: playState
        }
      }
      return {}
    }
    const handleClick = (index: number) => goTo(index)
    const handleAnimation = () => {
      if (hasNext) goTo(currentIndex + 1)
    }

    return (
      <div
        className={classNames('carousel', className)}
        onMouseEnter={() => setPlayState('paused')}
        onMouseLeave={() => setPlayState('running')}
      >
        {/* 轮播区域 */}
        <div
          className="carousel-container"
          style={rootContainerStyle}
        >
          {
            children?.map((item, idx) => (
              <div
                className={classNames('carousel-item', { 'carousel-item-active': currentIndex === idx })}
                key={'carousel-item' + idx}
                style={{ width: `${100 / childrenCount.current}%`, }}>
                {item}
              </div>
            ))
          }
        </div>

        {/* 按钮区域 */}
        {
          enableDots &&
          <div className={dtsClass}>
            {
              children.map(((_, idx) => (
                <button
                  className={classNames('dots-item', { 'dots-item-active': currentIndex === idx })}
                  key={idx}
                  onClick={() => handleClick(idx)}
                >
                  <div
                    className='dots-item-span'
                    style={dtsStyle(idx)}
                    onAnimationEnd={() => handleAnimation()}
                  >{idx}</div>
                </button>
              )))
            }
          </div>
        }
      </div>
    )
  }
)


export * from './interface'
export default Carousel;
