import { CarouselContainer } from './Carousel';
import { CarouselItem } from './CarouselItem';

type Component = typeof CarouselContainer;

interface CarouselType extends Component {
  Item: typeof CarouselItem;
}

const Carousel = CarouselContainer as CarouselType;

Carousel.Item = CarouselItem;

export default Carousel;
