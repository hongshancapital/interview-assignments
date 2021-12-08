import React, { FC, useCallback, useState, useEffect } from "react";

import "./Carousel.css";
import CarouselIndicator from "./CarouselIndicator";

const DEFAULT_ANTIMATION_TIME = 2000;

export interface CarouselProps {
    animationTime?: number;
}

const Carousel: FC<CarouselProps> = ({
    children,
    animationTime = DEFAULT_ANTIMATION_TIME,
}) => {
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
        }, animationTime);

        return () => {
            if (interval) {
                clearInterval(interval);
            }
        };
    }, [switchToNextItem, animationTime]);

    return (
        <div className="carousel">
            <div
                className="carousel__inner "
                style={{ transform: `translateX(-${activeItem * 100}%)` }}
            >
                {children}
            </div>
            <div className="carousel__indicators-wrapper">
                <div className="carousel__indicators">
                    {React.Children.map(children, (child, index) => (
                        <CarouselIndicator
                            active={activeItem === index}
                            animationTime={animationTime}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Carousel;
