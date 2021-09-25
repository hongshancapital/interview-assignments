import { CSSProperties, HTMLAttributes } from "react";
import { IElementSize, UnionOmit } from "src/util";

export type PaginationPosition = 'left' | 'center' | 'right';

export interface ICarouselOptions {
  delay: number;
  speed: number;
  autoplay: boolean;
  paginationColor: string;
  paginationActiveColor: string;
  paginationPosition: PaginationPosition;
}

export interface ICarouselWrapper {
    onActiveChange?: (currentIndex: number) => void;
    children?: React.ReactNode;
}
  
export type ICarouselWrapperProps = { extra: Partial<ICarouselOptions> } &
    UnionOmit<ICarouselWrapper, HTMLAttributes<HTMLDivElement>>;
  
export interface CarouselWrapperRefType {
    goTo: (nextIndex: number) => void;
    prev: () => void;
    next: () => void;
}

 
export interface IModel{
   currentIndex: number;
   preIndex: number | null;
   sliderLen: number;
   carouselSize: IElementSize;
   containerTransform: CSSProperties
}

export type IModelAction = {
    type: 'UPDATE_STATE',
    payload: Partial<IModel>
} 