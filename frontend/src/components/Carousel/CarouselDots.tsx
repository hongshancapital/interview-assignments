import type { SwiperTrigger } from './useSwiper'

export interface CarouselDotsProps {
    duration?:number,
    activeIndex:number,
    size:number,
    onClick:SwiperTrigger
}

const CarouselDots:React.FC<CarouselDotsProps> = (props) => {
    const {
        duration = 3,
        size,
        activeIndex,
        onClick
    } = props
    return (
        <dl className='carousel-dots'>{Array.from({ length: size }).map((_, index) => (
            <li
                key={`carousel-dot-key-${index}`}
                onClick={() => onClick(index)}
                className={`${activeIndex === index ? 'carousel-dot active' : 'carousel-dot'}`}
                style={{ animationDirection: `${duration}s` }}    
            ></li>
        ))}</dl>
    )
}

export default CarouselDots