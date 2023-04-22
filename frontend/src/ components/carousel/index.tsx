import React, { useState } from "react";
import "./index.css"
import ProgressBar from "../progress";

interface CarouselProps {
    images: string[];
    intervalTime: number
}

const Carousel: React.FC<CarouselProps> = ({ images, intervalTime = 3000 }) => {
    const [currentIndex, setCurrentIndex] = useState<number>(0);
    const nextPic = (): void => {
        if (currentIndex === images.length - 1) {
            setCurrentIndex(0);
            return
        }
        setCurrentIndex(currentIndex + 1);
    };

    return (
        <div className="carousel">
            <div className="carousel-images">
                {images.map((url, index) => (
                    <img
                        key={index}
                        src={url}
                        alt={`url ${index}`}
                        style={{ transform: `translateX(-${currentIndex * 100}%)` }}
                    />
                ))}
            </div>
            <div className="carousel-indicators">
                {images.map((_, index) => (
                    <ProgressBar key={_} isStart={index === currentIndex} totalTime={intervalTime} onComplete={nextPic} />
                ))}
            </div>
        </div>
    );
};

export default Carousel;
