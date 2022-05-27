import Carousel from "./Carousel"
import CarouselItem from './Item'

type CarouselType = typeof Carousel

interface CarouselItemType extends CarouselType {
	Item: typeof CarouselItem
}

const NewCarousel = Carousel as CarouselItemType

NewCarousel.Item = CarouselItem

export default NewCarousel