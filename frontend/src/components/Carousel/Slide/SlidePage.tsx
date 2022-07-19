import React, { memo } from "react";
import { carouselItem } from "../CarouselModel";

interface SlidePageProps {
    slide: carouselItem;
};

const SlidePage:React.FC<SlidePageProps> = ({slide}) => {
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
        console.log("update descripton ", slide.descriptions)
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
    
    return (
        <div
            className="slice_page"
            style={slicePageStyle(slide)}
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
}

export default memo(SlidePage); // The slide page should render only once.