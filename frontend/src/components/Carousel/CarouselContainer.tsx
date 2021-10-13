import React from 'react';
import { prefix } from '../util';
export interface CarouselContainerProps {
  /** Carousel container width */
  width?: string;
  /** Carousel container height */
  height?: string;
  /** container dom ref forward to parent */
  ref?: React.Ref<HTMLElement>;
}
export const CarouselContainer: React.FC<CarouselContainerProps> = React.forwardRef(
  ({ width='100%', height="100%", children }, forwardedRef) => {
    return (
      <div ref={forwardedRef as any} className={prefix('carousel carousel-container')} style={{width,height}}>
        {children}
      </div>
    );
  }
);
