  import {
    ForwardRefExoticComponent,
    PropsWithoutRef,
    RefAttributes,
  } from 'react';

  
  import {
    ICarouselProps,
    Carousel as CarouselComponent,
    CarouselRefType,
  } from './Carousel';
  
  import {
    CarouselPagination,
  } from './CarouselPagination';

  import { CarouselSlider } from './CarouselSlider';
  
  import "./index.css";

  type CarouselComponentType = ForwardRefExoticComponent<
    PropsWithoutRef<ICarouselProps> & RefAttributes<CarouselRefType>
  >;
  
  interface CarouselType extends CarouselComponentType {
    Slider: typeof CarouselSlider;
    Pagination: typeof CarouselPagination;
  }

  const Carousel = CarouselComponent as CarouselType;
  
  Carousel.Slider = CarouselSlider;

  Carousel.Pagination = CarouselPagination;


  
  export default Carousel


 
  