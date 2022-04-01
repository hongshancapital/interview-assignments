import React, { FC, MouseEvent } from 'react';
import classnames from 'classnames';

import { CarouselIndicatorProps, InternalIndicatorProps } from './interface';
import { isFunc } from './utils';

export const INDICATOR_DISPLAY_NAME = 'Indicator';

const prefixCls = 'pr-carousel-indicator';

export const Indicator: FC<CarouselIndicatorProps & InternalIndicatorProps> = (
  props
) => {
  const {
    className,
    style,
    position = 'bottom',
    triggerType = 'click',
    shape = 'line',
    slidesLength = 0,
    activeIndex = 0,
    interval,
    autoplay,
    slideTo,
  } = props;
  // NOTE: if the length of slides are zero, it'll return null quickly
  if (slidesLength <= 0) return null;

  const isValidPosition = ['bottom', 'left', 'right', 'top', 'outer'].includes(
    position
  );
  const isValidShape = ['dot', 'line'].includes(shape);
  const classNames = classnames(
    prefixCls,
    {
      [`${prefixCls}-${position}`]: isValidPosition,
      [`${prefixCls}-${shape}`]: isValidShape,
    },
    className
  );
  /**
   * NOTE: There is a bug here, maybe it's not.
   *
   * 1. when mouse enter the container, we'll clear the current timer of setInterval, However, the animation is running,
   * so it'll lead to mistiming between animation and the (new) timer.
   *
   * 2. Why is not? In general, we don't set the「interval」long. so the effect is not obvious.
   */
  const itemLineStyle =
    shape === 'line'
      ? {
          animationDuration: `${interval}ms`,
          animationPlayState: autoplay ? 'running' : 'paused',
        }
      : {};

  const handleSlide = (e: MouseEvent) => {
    /**
     * NOTE: 1, e.target VS. e.currentTarget
     * - e.target: the element of trigger event
     * - e.currentTarget: the element of binding event
     */
    const strIdx = (e.target as HTMLElement)?.getAttribute('data-index');
    if (strIdx && slideTo && isFunc(slideTo)) {
      slideTo(Number(strIdx));
    }
  };
  const clickEvents =
    triggerType === 'click'
      ? {
          onClick: handleSlide,
        }
      : {};
  const hoverEvents =
    triggerType === 'hover'
      ? {
          onMouseEnter: handleSlide,
        }
      : {};

  return (
    <div className={classNames} style={style} {...clickEvents}>
      {new Array(slidesLength).fill(0).map((_, i) => (
        <div
          data-index={i}
          {...hoverEvents}
          className={classnames(`${prefixCls}-item`, {
            [`${prefixCls}-item-active`]: activeIndex === i,
          })}
          key={i}
        >
          {autoplay && shape === 'line' && activeIndex === i && (
            <span
              style={itemLineStyle}
              className={`${prefixCls}-item-animate`}
              key={i}
            />
          )}
        </div>
      ))}
    </div>
  );
};

Indicator.displayName = INDICATOR_DISPLAY_NAME;
