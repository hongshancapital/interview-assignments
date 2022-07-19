import { createContext, useContext } from 'react';
import { AspectRatio } from '../aspect';

export type CarouselContextType = {
  ratio: AspectRatio;
  current: number;
  total: number;
  interval: number;
  duration: number;
  changeIndex: (index: number) => void;
  switching: false | number;
  isPause: boolean;
  pauseIndex: number;
  pause: () => void;
  resume: () => void;
}

export const CarouselContext = createContext<CarouselContextType>({
  ratio      : '16:9',
  current    : 0,
  total      : 0,
  interval   : 3000,
  duration   : 500,
  changeIndex: () => undefined,
  switching  : false,
  isPause    : false,
  pauseIndex : -1,
  pause      : () => undefined,
  resume     : () => undefined,
});

export const useCarouselContext = () => useContext<CarouselContextType>(CarouselContext);
