import React, { useEffect, useRef, useState } from "react";
import { useCarouselControl } from "./hooks/useCarouselControl";
import "./index.css";
import { CarouselOptions } from "./type";

const LOOP_INTERVAL = 4000;

export function Carousel({ options = [] }: { options: CarouselOptions[] }) {
    const carouselWrapperRef = useRef<HTMLDivElement>(null);
    const [Pagination, slideTo] = useCarouselControl(options.length);
    const [carouselIndex, setCarouselIndex] = useState(0);

    // start loop
    useEffect(() => {
        const loopHandler = () => {
            const newIndex =
                ((carouselIndex || 0) + 1) % (options?.length || 0);
            carouselWrapperRef.current?.children?.[newIndex].scrollIntoView({
                behavior: "smooth",
            });
            slideTo(newIndex);
            setCarouselIndex(newIndex);
        };
        const timerId = window.setInterval(loopHandler, LOOP_INTERVAL);
        return () => clearInterval(timerId);
    }, [slideTo, options, carouselIndex]);

    return (
        <React.Fragment>
            <div className="container" ref={carouselWrapperRef}>
                {options.map(
                    ({
                        title,
                        desc,
                        backgroundColor,
                        backgroundImg,
                        color = "#000",
                    }) => {
                        return (
                            <div
                                className="slide"
                                style={{ backgroundColor, color }}
                                key={title}
                            >
                                <div className="content">
                                    <div className="title">{title}</div>
                                    {desc ? (
                                        <div className="desc">{desc}</div>
                                    ) : null}
                                </div>

                                <div className="icon">
                                    <img src={backgroundImg} alt={title} />
                                </div>
                            </div>
                        );
                    }
                )}
            </div>

            {Pagination}
        </React.Fragment>
    );
}
