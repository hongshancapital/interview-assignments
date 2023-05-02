import { createContext } from 'react';
import { SliderWidth } from '../index.d';

const defaultValue: SliderWidth = {
    slideWidth:0
};
export const SliderWrapperContent = createContext<SliderWidth>(defaultValue);
SliderWrapperContent.displayName = 'SliderWrapperContent';
