import CarouselSlider from "./CarouselSlider";
import CarouselItem from "./CarouselItem";
import './index.scss';

type CompoundedComponent = typeof CarouselSlider & {
    Item: typeof CarouselItem
}

const Carousel = CarouselSlider as unknown as CompoundedComponent;
Carousel.Item = CarouselItem;

export default Carousel;