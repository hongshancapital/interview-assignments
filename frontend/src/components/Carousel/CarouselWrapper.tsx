import { Children } from 'react'
import CarouselDots from './CarouselDots'
import useSwiper from './useSwiper'
import type { SwiperOption } from './useSwiper'
import type { CarouselItemProps } from './CarouselItem'

export interface CarouselProps extends SwiperOption {
    children:React.ReactElement<CarouselItemProps>[]
}

const Carousel:React.FC<CarouselProps> = (props) => {
    const {
        children,
        delay,
        ...other
    } = props
    const count = Children.count(children)
    const [activeIndex, go] = useSwiper(count, other)
    const clientWidth = document.documentElement.clientWidth
    return (
        <div className='carousel-wrapper'>
            <div
                className='carousel'
                style={{ width: count * clientWidth, transform: `translateX(${-activeIndex * clientWidth}px)` }}
            >{children}</div>
            <CarouselDots
                duration={delay}
                activeIndex={activeIndex}
                size={count}
                onClick={go}
            ></CarouselDots>
        </div>
    )
}

export default Carousel