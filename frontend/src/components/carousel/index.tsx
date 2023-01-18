import { Swipe } from "./Swipe";
import { SwipeItem } from "./SwipeItem";

type Component = typeof Swipe;

interface SwipeInterface extends Component {
  Item: typeof SwipeItem
}

const Carousel = Swipe as SwipeInterface;

Carousel.Item = SwipeItem;

export {Carousel};
