import React, { CSSProperties, ReactNode } from 'react';
import { Alignment } from './types';

const getSliderListWidth = (
  count: number,
  slidesToShow?: number
): string => {
  const visibleSlides = slidesToShow || 1;
  const percentage = (count * 100) / visibleSlides;
  return `${percentage}%`;
};

const getTransition = (
  count: number,
  initialValue: number,
  currentSlide: number,
  cellAlign: 'left' | 'right' | 'center',
): number => {
  if (cellAlign === Alignment.Left) {
    const slideTransition = (100 / count) * currentSlide;
    return -(slideTransition + initialValue);
  } else if (cellAlign === Alignment.Center) {
    const slideTransition = (100 / count) * currentSlide;
    return initialValue - slideTransition;
  } else if (cellAlign === Alignment.Right) {
    const slideTransition = (100 / count) * currentSlide;
    return initialValue - slideTransition;
  }

  return initialValue;
};

// eslint-disable-next-line complexity
const getPositioning = (
  cellAlign: 'left' | 'right' | 'center',
  slidesToShow: number,
  count: number,
  currentSlide: number,
  move?: number
): string => {
  const totalCount = count;
  const slideSize = 100 / totalCount;
  let initialValue = 0;

  if (cellAlign === Alignment.Right && slidesToShow > 1) {
    const excessSlides = slidesToShow - 1;
    initialValue += slideSize * excessSlides;
  }

  if (cellAlign === Alignment.Center && slidesToShow > 1) {
    const excessSlides = slidesToShow - 1;
    // Half of excess is on left and half is on right when centered
    const excessLeftSlides = excessSlides / 2;
    initialValue += slideSize * excessLeftSlides;
  }

  const horizontalMove = getTransition(
    count,
    initialValue,
    currentSlide,
    cellAlign
  );

  const draggableMove = move
    ? `calc(${horizontalMove}% - ${move}px)`
    : `${horizontalMove}%`;
  return `translate3d(${draggableMove}, 0, 0)`;
};

export const getSliderListStyles = (
  children: ReactNode | ReactNode[],
  currentSlide: number,
  animation: boolean,
  slidesToShow?: number,
  cellAlign?: 'left' | 'right' | 'center',
  speed?: number,
  move?: number,
  slideAnimation?: 'fade' | 'zoom'
): CSSProperties => {
  const count = React.Children.count(children);

  const width = getSliderListWidth(count, slidesToShow);
  const positioning = getPositioning(
    cellAlign || Alignment.Left,
    slidesToShow || 1,
    count,
    currentSlide,
    move
  );

  return {
    width,
    textAlign: 'left',
    height: 'inherit',
    transition:
      animation && slideAnimation !== 'fade'
        ? `${speed || 500}ms ease 0s`
        : 'none',
    transform: positioning,
    display: 'flex'
  };
};
