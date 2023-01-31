import Carousel, { ICarouselProps} from "./Carousel";
import CarouselItem from "./CarouselItem";

export type { ICarouselProps } from "./Carousel";
export type { ICarouselItemProps } from "./CarouselItem";

interface CarouselInterface
  extends React.ForwardRefExoticComponent<
    ICarouselProps & React.RefAttributes<HTMLElement>
  > {
  Item: typeof CarouselItem;
}

(Carousel as CarouselInterface).Item = CarouselItem;

export default Carousel as CarouselInterface;
