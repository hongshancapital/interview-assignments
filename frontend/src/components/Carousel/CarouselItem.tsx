import React, { useImperativeHandle, useMemo, useRef, useState } from 'react';

export interface SwipeItemRef {
  setOffset: React.Dispatch<React.SetStateAction<number>>;
}

export interface SwipeItemProps {
  readonly style?: React.CSSProperties;
  children: React.ReactNode;
}

export const CarouselItem = React.forwardRef<SwipeItemRef, SwipeItemProps>(
  ({ children, style }, ref) => {
    const [offset, setOffset] = useState(0);

    const swipeItemRef = useRef<HTMLDivElement>(null);

    useImperativeHandle(ref, () => {
      return {
        setOffset
      };
    });

    const itemStyle = useMemo(
      () => ({
        transform: offset ? `translateX(${offset}px)` : '',
        ...style
      }),
      [offset, style]
    );

    return (
      <div ref={swipeItemRef} className='carousel-item' style={itemStyle}>
        {children}
      </div>
    );
  }
);
