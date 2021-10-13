import React from 'react';
import { prefix } from '../util';

export const CarouselItem: React.FC<{}> = function ({children}) {
  return <div className={prefix('carousel-item')}>{children}</div>;
};
