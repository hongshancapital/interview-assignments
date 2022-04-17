import React, { ReactElement } from 'react';
import './index.scss';
import { useCarouselIndex } from '../../hooks/CarouselIndex';

interface Slide {
    class: string;
    title: (string | ReactElement);
    text?: (string | ReactElement);
}

interface CarouselProps {
    slides: Slide[];
    duration: number;
}

function Carousel(props: CarouselProps) {

    let currentIndex = useCarouselIndex(props.slides.length, props.duration);

    const carouselSlides = <div
        className="carousel-items"
        style={{ transform: `translate(-${currentIndex * 100}%)` }}
    >
        {props.slides.map((slide, index) => (
            <div className={`slide ${slide.class}`} key={`slide_${index}`}>
                <div className="words">
                    <div className="title">{slide.title}</div>
                    {slide?.text && <div className="text">{slide.text}</div>}
                </div>
                <div className="image"></div>
            </div>
        ))}
    </div>;

    const carouselIndicators = <div className="carousel-indicators">
        {Array.from({ length: props.slides.length }).map((_, index) => (
            <div className="indicator-container" key={`indicator_${index}`}>
                <div
                    className={`indicator-process${index === currentIndex ? ' current' : ''}`}
                    style={{ transition: `transform ${props.duration}ms ease-in-out` }}
                ></div>
            </div>
        ))}
    </div>;

    return (
        <div className="carousel-window">
            {carouselSlides}
            {carouselIndicators}
        </div>
    );
}

export default Carousel;