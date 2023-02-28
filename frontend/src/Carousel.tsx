import React from "react";
import { Swiper, SwiperItem } from './components/Swiper';

import Style from './Carousel.module.scss'

import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

import Banner, { BannerProps } from "./components/Banner/Banner";


const banners: BannerProps[] = [
    {
        title: ['xPhone'],
        desc: ['Lots to love.Less to spend.', 'Starting at $399'],
        style: {
            color: '#fff',
            backgroundImage: `url(${iphone})`,
            backgroundColor: '#111111'
        }
    },
    {
        title: ['Tablet'],
        desc: ['Just the right amount of everythings'],
        style: {
            color: '#000',
            backgroundImage: `url(${tablet})`,
            backgroundColor: '#fafafa'
        }
    },
    {
        title: ['Buy a Tablet or xPhone for college.', 'Get arPods'],
        desc: [],
        style: {
            color: '#000',
            backgroundImage: `url(${airpods})`,
            backgroundColor: '#f1f1f3'
        }
    },
]



const Carousel: React.FC = () => {
    return <div className={Style["container"]}>
        <Swiper>
            {banners.map((prop, index) => <SwiperItem key={index}><Banner {...prop} /></SwiperItem>)}
        </Swiper>
    </div>
}

export default Carousel