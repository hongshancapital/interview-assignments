
import React, { HTMLAttributes } from 'react';
import { classnames, getPrefixCls } from '../../util';
import { UnionOmit } from './interface';

export interface ICarouselSlider {
  children?: React.ReactNode;
}

export type ICarouselSliderProps = UnionOmit<
  ICarouselSlider,
  HTMLAttributes<HTMLDivElement>
>;

export const CarouselSlider = (props: ICarouselSliderProps): JSX.Element => {
  const { children, className, ...extra } = props;
  const sliderItemCls = getPrefixCls('carousel-slider');

  return (
    <div {...extra} className={classnames(sliderItemCls, className)}>
      {children}
    </div>
  );
};
