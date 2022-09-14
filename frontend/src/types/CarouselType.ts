import { CSSProperties } from 'react';

export interface CarouselItemType {
    id: string;
    title?: string;
    describe?: string;
    image?: string;
  }

export type CarouselItemPropsType = Omit<CarouselItemType, "id">

export interface CarouselPropsType {
    source?: CarouselItemType[];
    timer?: number;
    defaultIndex?: number;
    style?: CSSProperties;
  }



