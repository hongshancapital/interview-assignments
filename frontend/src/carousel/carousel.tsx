import React from 'react';

interface ICarouselProps {
  children: JSX.Element | JSX.Element[];
}

export function Carousel(props: ICarouselProps): JSX.Element {
  return (
    <div>
      {props.children}
    </div>
  );
}