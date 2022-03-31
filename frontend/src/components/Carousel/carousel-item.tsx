import React, { FC } from 'react';
import classnames from 'classnames';

import { CarouselItemProps, InternalCarouselItemProps } from './interface';

export const ITEM_DISPLAY_NAME = 'CarouselItem';

export const itemPrefixCls = 'pr-carousel-item';

export const CarouselItem: FC<CarouselItemProps & InternalCarouselItemProps> = (
  props
) => {
  const { className, style, index, activeIndex, children } = props;

  const classNames = classnames(
    itemPrefixCls,
    {
      [`${itemPrefixCls}-active`]: index === activeIndex,
    },
    className
  );

  return (
    <li className={classNames} style={style}>
      {children}
    </li>
  );
};

CarouselItem.displayName = ITEM_DISPLAY_NAME;
