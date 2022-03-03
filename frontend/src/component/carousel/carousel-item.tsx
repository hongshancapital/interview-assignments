import React from 'react';
import { CarouselItemProps } from './index';

interface Props extends CarouselItemProps {
  defaultStyle?: React.CSSProperties;
}

export default function CarouselItem(props: Props) {
  const {
    className,
    style,
    children,
    defaultStyle,
  } = props;

  return(
    <div data-role="carousel-item" className={className} style={{ ...style, ...defaultStyle }}>
      {children}
    </div>
  )
}
