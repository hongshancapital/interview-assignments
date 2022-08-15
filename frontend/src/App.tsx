import React from "react";
import Carousel from "./components/Carousel";
import { ICarouselItemProps } from "./components/Carousel/CarouselItem";
import "./App.css";

import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';
import airpodsImg from './assets/airpods.png';

function App() {
    const items: ICarouselItemProps[]  = [
        {
            title: ['xPhone'],
            desc: ['Lots to love. Less to spend.', 'Starting at $399'],
            img: iphoneImg,
            titleColor: '#fffff0',
            bgColor: '#111111'
        },
        {
            title: ['Tablet'],
            desc: ['Just the right amount of everything'],
            img: tabletImg,
            titleColor: '#000000',
            bgColor: '#fafafa'
        },
        {
            title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
            img: airpodsImg,
            titleColor: '#000000',
            bgColor: '#f1f1f3'
        },
    ]
    return (
        <div className="App">
            <Carousel items={items}></Carousel>
        </div>
    )
}

export default App;
