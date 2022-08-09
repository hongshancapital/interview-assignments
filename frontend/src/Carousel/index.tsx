import React from "react";
import './index.css';
const SlickCarousel = require('react-slick').default;

interface CarouselProps {
    autoplay?: boolean;
    dots?: boolean;
    arrows?: boolean;
    draggable?: false
}

const Carousel: React.FC<CarouselProps> = props => {
    return (
        <div className="carousel">
            <SlickCarousel {...props} />
        </div>
    )
}

Carousel.displayName = 'Carousel';
Carousel.defaultProps = {
    dots: true,
    arrows: false,
    draggable: false
}

export default Carousel;
