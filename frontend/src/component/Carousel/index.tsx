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


  import { CarouselSlider } from './CarouselSlider';
  
  import "./index.css";

  type CarouselComponentType = ForwardRefExoticComponent<
    PropsWithoutRef<ICarouselProps> & RefAttributes<CarouselRefType>
  >;
  
  interface CarouselType extends CarouselComponentType {
    Slider: typeof CarouselSlider;
  }

  const Carousel = CarouselComponent as CarouselType;
  
  Carousel.Slider = CarouselSlider;

  
  export default Carousel


 
  