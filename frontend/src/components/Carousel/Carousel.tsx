import React, { FC, useCallback, useState, useEffect } from "react";

import "./Carousel.css";
import CarouselIndicator from "./CarouselIndicator";

const ANTIMATION_TIME = 2000;

interface CarouselProps {}

const Carousel: FC<CarouselProps> = ({ children }) => {
    const [activeItem, setActiveItem] = useState<number>(0);
    const childrenNum = React.Children.count(children);

    const switchActiveItem = useCallback(
        (activeItem: number) => {
            if (activeItem < 0) {
                setActiveItem(childrenNum - 1);
            } else if (activeItem >= childrenNum) {
                setActiveItem(0);
            } else {
                setActiveItem(activeItem);
            }
        },
        [setActiveItem, childrenNum]
    );
    const switchToNextItem = useCallback(() => {
        switchActiveItem(activeItem + 1);
    }, [switchActiveItem, activeItem]);

    useEffect(() => {
        const interval = setInterval(() => {
            switchToNextItem();
        }, ANTIMATION_TIME);

        return () => {
            if (interval) {
                clearInterval(interval);
            }
        };
    }, [switchToNextItem]);

    return (
        <div className="carousel">
            <div
                className="carousel__inner"
                style={{ transform: `translateX(-${activeItem * 100}%)` }}
            >
                {children}
            </div>
            <div className="carousel__indicators">
                {React.Children.map(children, (child, index) => (
                    <CarouselIndicator
                        active={activeItem === index}
                        animationTime={ANTIMATION_TIME}
                    />
                ))}
            </div>
        </div>
    );
};

export default Carousel;
