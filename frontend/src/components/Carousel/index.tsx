import React, { useRef } from "react";
import { useCarouselControl } from "./hooks/useCarouselControl";
import "./index.css";
import { CarouselOptions } from "./type";

export function Carousel({ options }: { options: CarouselOptions[] }) {
    const carouselWrapperRef = useRef<HTMLDivElement>(null);
    const [Pagination] = useCarouselControl(carouselWrapperRef);

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
