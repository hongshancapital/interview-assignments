import React, { useMemo, CSSProperties } from 'react';
import classnames from 'classnames';
import { useCarousel } from './use-carousel';

import './index.css';

export interface ICarouselProps {
  // 用户自定义容器类名
  className?: string;
  // 用户自定义容器样式
  style?: CSSProperties;
  // 默认选择
  defaultActiveIndex?: number;
  // 切换事件
  duration?: number;
  // 动画时间
  transitionDuration?: number;
}

/**
 * 走马灯实现组件，支持将子节点转化为跑马灯播放模式
 * <Carousel>{Children Element}</Carousel>
 * @param {ICarouselProps} 组件参数
 */
export const Carousel: React.FC<ICarouselProps> = ({
  children,
  className,
  style,
  defaultActiveIndex = 0,
  duration = 3000,
  transitionDuration = 400,
}) => {
  const validChildList = useMemo(() => React.Children.toArray(children).filter(React.isValidElement), [children]);
  // 走马灯内容数量
  const count = useMemo(() => validChildList.length, [validChildList]);

  const {curIndex, handleToStep} = useCarousel({defaultActiveIndex, duration, count});

  const contentStyle: CSSProperties = useMemo(() => {
    return {
      width: `${count*100}%`,
      transform: `translateX(${-curIndex / count * 100}%)`,
      transitionDuration: `${transitionDuration / 1000}s`,
    }
  }, [curIndex, count, duration, transitionDuration]);


  return <div className={classnames('carousel', className)} style={style}>
    <div className="carousel-content" style={contentStyle}>
      {
        validChildList.map((child, index) => {
          return <div key={index} className="carousel-item" style={{ width: `${ 100/count }%` }}>{child}</div>;
        })
      }
    </div>
    <div className="carousel-step">
      {
        Array.from(Array(count).keys()).map((key, idx) => {
          const isActive = curIndex === idx;
          // 加一层按钮容器，方便用户点击操作
          return <div key={key} className="carousel-step-content" onClick={() => handleToStep(idx)}>
            <div className={classnames('carousel-step-item', { 'carousel-step-item-active': isActive })}>
              {
                isActive ? <div className="carousel-step-progress" style={{ animationDuration: `${duration / 1000}s` }}></div> : null
              }
            </div>
          </div>
        })
      }
    </div>
  </div>;
};
