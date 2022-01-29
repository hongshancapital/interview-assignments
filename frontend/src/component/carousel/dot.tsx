import React from 'react';
import { DotProps } from './index';

export default function Dot(props: DotProps) {
  const {
    active,
    duration,
    dotPosition,
  } = props;

  const animationName = ['left', 'right'].includes(dotPosition) ? 'dot-height' : 'dot-width';

  return(
    <div className="carousel-dot-item">
      <div className="carousel-dot-item-info" />
      <div className="carousel-dot-item-info carousel-dot-item-background" style={{ ...active && { animation: `${animationName} ${duration}ms 1` } }} />
    </div>
  )
}
