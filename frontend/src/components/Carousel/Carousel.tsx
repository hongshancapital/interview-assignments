import React, { useEffect, useState } from 'react';
import { useMouseInRect } from '../../hooks';
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

const startTime = new Date().getTime();
// let renderCallCount = 0;

const Carousel: React.FC<CarouselProps> = function (props) {
  debug('[Carousel:render]');

  const { overPause = false, showIndicator = true, showNavi = false, duration = 3000, children } = props

  const [step, setStep] = useState(0);

  const [rectRef, mouseover] = useMouseInRect();

  const count = React.Children.count(children);

  useEffect(() => {
    const eatTime = Math.floor((new Date().getTime() - startTime) / 1000);

    debug('[Carousel:useEffect] step=%s eatTime=%ss', step, eatTime);

    const timer = setTimeout(() => {
      if (overPause && mouseover) return;
      setStep((step + 1) % count);
    }, duration);

    return () => {
      clearTimeout(timer);
    };
  }, [step, mouseover, overPause, count, duration]);

  return (
    <CarouselContainer ref={rectRef as React.Ref<HTMLElement>} {...props}>
      <CarouselTransition {...props} step={step}>
        {props.children}
      </CarouselTransition>

      {showNavi && <CarouselNavi {...props} />}

      {showIndicator && <CarouselIndicator {...props} step={step} count={count} />}
    </CarouselContainer>
  );
};

const defineCarouselProps = (props: CarouselProps): CarouselProps => {
  return props;
};

export { Carousel, CarouselItem, defineCarouselProps };
