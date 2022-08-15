import React from "react";
import { Swipe, SwipeItem } from './components/Swipe';

import Style from './Carousel.module.scss'

import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';


const THEME_COLOR_MAP: { [key: string]: string } = {
    light: '#000',
    dark: '#fff'
}

interface BannerProps {
    title: Array<string>;
    desc: Array<string>;
    picture: string;
    theme: string;
    backgroundColor: string;
}

const banners: BannerProps[] = [
    {
        title: ['xPhone'],
        desc: ['Lots to love.Less to spend.', 'Starting at $399'],
        picture: iphone,
        theme: 'dark',
        backgroundColor: '#111111'
    },
    {
        title: ['Tablet'],
        desc: ['Just the right amount of everythings'],
        picture: tablet,
        theme: 'light',
        backgroundColor: '#fafafa'
    },
    {
        title: ['Buy a Tablet or xPhone for college.', 'Get arPods'],
        desc: [],
        picture: airpods,
        theme: 'light',
        backgroundColor: '#f1f1f3'
    },
]

const Banner = (props: BannerProps) => {
    return <div style={{
        color: THEME_COLOR_MAP[props.theme],
        backgroundImage: `url(${props.picture})`,
        backgroundColor: props.backgroundColor
    }} className={Style["banner"]}>
        <div className={Style["slogan"]}>
            {props.title.map(item => <h1 key={item}>{item}</h1>)}
            {props.desc.map(item => <p key={item}>{item}</p>)}
        </div>
    </div>
}

const Carousel: React.FC = () => {
    return <div className={Style["container"]}>
        <Swipe>
            {banners.map((prop, index) => <SwipeItem key={index}><Banner {...prop} /></SwipeItem>)}
        </Swipe>
    </div>
}

export default Carousel