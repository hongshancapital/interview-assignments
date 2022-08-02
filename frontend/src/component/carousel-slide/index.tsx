import React from "react";
import { IConfig } from "../../config";
import './index.css';

interface CarouselSlideProps {
    info: IConfig;
}
export const CarouselSlide = (props: CarouselSlideProps) => {
    const { info } = props;
    return (<div style={info.style} className="carousel-slide">
        <div className="text-container">
            {info.titleStrings.map((title, index) => {
                return <div key={index} className="title">{title}</div>
            })}
            {info.textStrings.map((text, index) => {
                return <div key={index} className="text">{text}</div>
            })}
        </div>
        <div className="image" style={{
              backgroundImage: `url(${info.image})`
        }}/>
    </div>)
};