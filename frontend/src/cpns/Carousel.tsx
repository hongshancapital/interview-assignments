import React, { useState } from "react";
import "./Carousel.css";
import { Carousel as AntdCarousel } from 'antd';
import { dataSorce } from "./data";
import CarouselItem from "./CarouselItem";


function Carousel() {
    const [dataSorce1, setDataSorce1] = useState(dataSorce);

    return (
    <div className="Carousel">
        {

        }
        <AntdCarousel autoplay className="AntdCarousel" dots={{className: 'dotsClass'}}>
            {
                dataSorce1 && dataSorce1.map(data => {
                    return <CarouselItem  data={data}/>
                })
            }
        </AntdCarousel>
    </div>
    );
}

export default Carousel;
