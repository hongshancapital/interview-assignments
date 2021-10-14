import React from 'react';
import { useMouse, useCounter, isMouseInRect } from '../../hooks';
import { debug } from '../util';
import { CarouselContainer, CarouselContainerProps } from './CarouselContainer';
import { CarouselIndicator } from './CarouselIndicator';
import { CarouselItem } from './CarouselItem';
import { CarouselNavi, CarouselNaviProps } from './CarouselNavi';
import { CarouselTransition, CarouselTransitionProps } from './CarouselTransition';
import './style.scss';

interface BaseCarouselProps {
  /** 是否显示导航控制 */
  showNavi: boolean;
  /** 是否显示进度指示器 */
  showIndicator: boolean;
  /** 鼠标滑过时是否暂停轮播 */
  overPause: boolean;
  /** 动画间隔时间 ms */
  duration: number;
}

export type CarouselProps = Partial<
  BaseCarouselProps & CarouselNaviProps & CarouselContainerProps & CarouselTransitionProps
>;

const Carousel: React.FC<CarouselProps> = function (props) {
  debug('[Carousel:render]');
  /* 属性默认值 */
  const {
    width = '100%',
    height = '100%',
    overPause = false,
    showIndicator = true,
    showNavi = false,
    duration = 3000,
    animation = 'slideLeft',
    children,
  } = props;

  const count = React.Children.count(children);
  const rectRef = React.useRef<HTMLElement>();

  useMouse();

  const { current: step } = useCounter({
    duration,
    maxCount: count,
    pauseWhen: () => {
      if(!overPause) return false;
      if(!rectRef.current) return false
      const _isMouseInRect = isMouseInRect(rectRef.current, 10)
      debug('[pauseWhen] isMouseInRect = %s', _isMouseInRect)
      return _isMouseInRect
    },
  });

  return (
    <CarouselContainer ref={rectRef as React.Ref<HTMLElement>} {...{ width, height }}>
      <CarouselTransition {...{ step, animation }}>{children}</CarouselTransition>
      {showNavi && <CarouselNavi {...props} />}
      {showIndicator && <CarouselIndicator {...{ step, duration, count }} />}
    </CarouselContainer>
  );
};

const defineCarouselProps = (props: CarouselProps): CarouselProps => {
  return props;
};

export { Carousel, CarouselItem, defineCarouselProps };
