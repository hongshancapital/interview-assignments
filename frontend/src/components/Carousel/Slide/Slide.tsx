import React, { useEffect, useMemo, useRef, useState } from "react";
import { carouselItem } from "../CarouselModel";
import "./Slide.scss";

interface IProps {
    carouselItems: carouselItem[];
    curIndex: number;
}

// slide places all slides in a line and can scroll through index to move correspond slide into slide window.
const Slide:React.FC<IProps> = ({carouselItems, curIndex}) => {
    const [slides, ] = useState<carouselItem[]>(() => carouselItems);
    const stageRef = useRef<HTMLDivElement>(null);

    const Title = (slide:carouselItem) => {
        return (
            <div className="slice_title">
                {
                    slide.titles.slice(0, 2).map(title => {
                        return <div key={title}>{title}</div>
                    })
                }
            </div>
        )
    };

    const Description = (slide:carouselItem) => {
        return (
            <div className="slice_description">
                {
                    slide.descriptions.slice(0, 2).map(description => {
                        return <div key={description}>{description}</div>
                    })
                }
            </div>
        )
    };

    const slicePageStyle = (slide:carouselItem) => {
        return {
            backgroundColor: slide.backgroundColor || "",
            color: slide.fontColor || "",
        }
    };

    // Move current slide into slide window.
    function moveSlide() {
        if(stageRef.current) {
            stageRef.current.style.transform = `translateX(${curIndex * -100}vw)`;
        }
    }

    useEffect(() => {
        moveSlide();
    }, [curIndex]);

    useEffect(() => {
        if(stageRef.current) {
            stageRef.current.style.width = `${slides.length * 100}vw`;
        }
    }, []);

    return (
        <div className="slide_collect" ref={stageRef}>
            {
                slides.map((slide, index) => {
                    return (
                        <div
                            className="slice_page"
                            style={slicePageStyle(slide)}
                            key={index}
                        >
                            <div className="slice_content">
                                { Title(slide) }
                                { Description(slide) }
                            </div>
                            <div className="slice_icon_container">
                                <img
                                    className="slice_icon"
                                    src={process.env.PUBLIC_URL + slide.icon}
                                />
                            </div>
                        </div>
                    )
                })
            }
        </div>
    )
}

export default Slide;