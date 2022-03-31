import React, { FC, MouseEvent } from 'react';
import classnames from 'classnames';

import { CarouselIndicatorProps, InternalIndicatorProps } from './interface';
import { isFunc } from './utils';

export const INDICATOR_DISPLAY_NAME = 'Indicator';

const prefixCls = 'pr-carousel-dot';

export const Indicator: FC<CarouselIndicatorProps & InternalIndicatorProps> = (
  props
) => {
  const {
    className,
    style,
    position = 'bottom',
    triggerType = 'click',
    shape = 'dot',
    slidesLength = 0,
    activeIndex = 0,
    slideTo,
  } = props;
  // NOTE: if the length of slides are zero, it'll return null quickly
  if (slidesLength <= 0) return null;

  const isValidPosition = ['bottom', 'left', 'right', 'top', 'outer'].includes(
    position
  );
  const classNames = classnames(
    prefixCls,
    {
      [`${prefixCls}-${position}`]: isValidPosition,
      [`${prefixCls}-${shape}`]: shape === 'line',
    },
    className
  );

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
        <span
          data-index={i}
          {...hoverEvents}
          className={classnames(`${prefixCls}-item`, {
            [`${prefixCls}-item-active`]: activeIndex === i,
          })}
          key={i}
        />
      ))}
    </div>
  );
};

Indicator.displayName = INDICATOR_DISPLAY_NAME;
