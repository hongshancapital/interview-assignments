import { createContext } from 'react';
import { CarouselContextType } from './types';

const CarouselContext = createContext<CarouselContextType>({ activedIndex: 0, slidesCount: 0 })

CarouselContext.displayName = 'CarouselContext'
export { CarouselContext }
