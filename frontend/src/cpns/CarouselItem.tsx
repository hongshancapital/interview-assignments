import React from "react";
import "./CarouselItem.css";
import { IData } from './types'

interface Iprops {
    data: IData
}

const CarouselItem = ({ data }: Iprops) => {
    return (
    <div className="Carousel">
        <div className="CarouselContent">
            <div style={{backgroundImage: `url('../assets/${data.imgUrl}')`, color: data.fontColor}} className="CarouselImg">
                <div className="CarouselFont">
                    <div className="CarouselTitle">{data.title}</div>
                    <div className="CarouselDescription">{data.description}</div>
                </div>
            </div>
        </div>
    </div>
    );
}

export default CarouselItem;