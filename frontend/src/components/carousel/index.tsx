import React from 'react';
import classNames from 'classnames'
import { CarouselProps, CarouselRef } from './interface';
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
    React.useEffect(() => {
      const currentCount = React.Children.count(props.children)
      if (childrenCount.current !== currentCount) {
        goTo(defaultIndex % currentCount, false);
        childrenCount.current = currentCount;
      }
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.children]);

    const [slider, setSlider] = React.useState(defaultIndex % childrenCount.current)
    const [useAnimation, setUseAnimation] = React.useState(true)

    const goTo = (index: number, animation: boolean = true) => {
      if (index !== slider) {
        beforeChange && beforeChange(slider, index)
        setSlider(index)
        afterChange && afterChange(index)
      }

      if (animation !== useAnimation) setUseAnimation(animation)
    }
    const next = () => goTo(slider + 1)
    const prev = () => goTo(slider - 1)
    const carouselRef = React.useRef<any>()
    React.useImperativeHandle(ref, () => ({
      el: carouselRef.current,
      goTo,
      next,
      prev,
    }))
    React.useEffect(() => {
      if (useAnimation === false) {
        setUseAnimation(true)
      }
    }, [useAnimation])


    // Dom 部分
    const rootClasses = classNames('carousel', className)
    const rootContainerStyle = {
      width: `${childrenCount.current * 100}%`,
      transitionDuration: useAnimation ? '0.5s' : '0s',
      transform: `translate3D(-${(100 * slider) / childrenCount.current}%, 0, 0)`,
    }
    const childrenClass = (idx: number) => classNames('carousel-item', { 'carousel-item-active': slider === idx })

    const hasNext = autoplay && duration !== 0
    const enableDots = !!dots
    const dtsClass = classNames([
      'dots',
      `dots-${dotPosition}`,
      typeof dots === 'boolean' ? '' : dots?.className
    ])
    const dtsChildClass = (idx: number) => classNames('dots-item', { 'dots-item-active': slider === idx })
    const dtsStyle = (idx: number): React.CSSProperties => {
      if (slider === idx) {
        const isX = ['top', 'bottom'].includes(dotPosition)
        const direction = isX ? 'width' : 'height'
        const animationName = isX ? 'progress-width' : 'progress-height'
        return {
          [direction]: hasNext ? 'auto' : '100%',
          animation: `${animationName} ${hasNext ? duration : 0}ms linear`
        }
      }
      return {}
    }
    const handleClick = (index: number) => goTo(index)
    const handleAnimation = () => {
      if (hasNext) goTo((slider + 1) % childrenCount.current)
    }

    return (
      <div ref={carouselRef} className={rootClasses}>
        {/* 轮播区域 */}
        <div
          className="carousel-container"
          style={rootContainerStyle}>
          {
            children?.map((item, idx) => (
              <div
                className={childrenClass(idx)}
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
                  className={dtsChildClass(idx)}
                  key={idx}
                  onClick={() => handleClick(idx)}
                >
                  <div
                    className='dots-item-span'
                    style={dtsStyle(idx)}
                    onAnimationEnd={() => handleAnimation()}
                  ></div>
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
