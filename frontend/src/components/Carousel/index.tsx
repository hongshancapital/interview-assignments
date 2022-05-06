import React from 'react'
import classnames from 'classnames'
import { IProps, useCarousel } from './useCarousel';
import styles from './index.module.css'

export function Carousel(props: IProps) {

  const { container, children, width, height, progress, interval, go, hover, out, handleNext, handlePrev } = useCarousel(props);
  const containerStyle = { width, height }

  const renderElement = (child: JSX.Element, index: number) => {
    return (
      <div key={index} style={{ left: index * width }} className={styles.element}>{child}</div>
    )
  }

  const renderHandler = () => {
    const handlerStyle = { width: height * 0.08, height: height * 0.08 }
    return [
      <div key="prev" data-testid="prev" onClick={handlePrev} style={handlerStyle} className={classnames([styles.controller, styles.controller_prev])}></div>,
      <div key="next" data-testid="next" onClick={handleNext} style={handlerStyle} className={classnames([styles.controller, styles.controller_next])}></div>
    ]
  }

  const renderIndicator = () => {
    return props.children && Array.isArray(props.children) && (
      <div className={styles.indicator}>
        {Array(props.children.length).fill(0).map((_, index: number) => {
          // 底部按钮过度动画，依赖轮播动画的间隔
          const animationStyle = { transition: progress === index ? `all ${interval / 1000}s linear` : 'none' }
          const classNameStr = classnames({
            [styles.indicator_item]: true,
            [styles.indicator_active]: progress === index,
          })

          return (
            <span data-testid={`indicator_${index}`} onClick={() => go(index + 1)} key={index} className={classNameStr}>
              <b style={animationStyle} />
            </span>
          )
        })}
      </div>
    );
  }

  return (
    <div className={styles.carousel_box} style={containerStyle} onMouseEnter={hover} onMouseLeave={out}>
      <div data-testid="elements" ref={container} className={styles.carousel_elements}>
        {React.Children.map(children.length < 2 ? children : [React.cloneElement(children[children.length - 1])].concat(children).concat(children[0]), renderElement)}
      </div>
      {props.control && renderHandler()}
      {props.indicator && renderIndicator()}
    </div>
  );
}