import React, { useEffect, useState } from "react"
import './index.css'

import banner1 from "../assets/airpods.png";
import banner2 from "../assets/iphone.png";
import banner3 from "../assets/tablet.png";

interface Img {
    url: String | any;
    bgColor: String | any;
    color: String | any;
}

const imgs: Img[] = [{url:banner1, bgColor: '#F1F1F3', color: 'black'}, 
{url:banner2, bgColor: '#111111', color: 'white'}, 
{url:banner3, bgColor: '#FAFAFA', color: 'black'}]

function useInterval(callback: Function, interval: number) {
    useEffect(() => {
        const start = new Date().getTime()
        const I = setInterval(() => {
            callback(new Date().getTime() - start)
        }, interval)
        return () => clearInterval(I)
    },  [])
}

function useSlider(N: number, speed = 3000){
    const [slider, setSlider] = useState(0)
    useInterval((diff: any) => {
        setSlider(_ => Math.floor(diff / speed) % N)
    }, 300)
    return slider
}

const Carousel = () => {
    const slider = useSlider(imgs.length)
    return (
        <div className="scroller">
            <div className="inner"
                style={{
                    width: `${imgs.length * 100}%`,
                    transform: `translateX(-${100 * slider/imgs.length}%)`
                }}>
                {imgs.map((img, index) => {
                    return (
                    <div className="img" key={index} style={{
                        width: `${100 / imgs.length}%`,
                        backgroundColor: img.bgColor,
                        color: img.color
                    }}>
                        <div className='title'>Buy a Tablet or xPhone for college.</div>
                        <div className='text'>Get airpods</div>
                        <img src={img.url} alt='' />
                    </div>)
                })}
            </div>
            <div className="slider">
                {imgs.map((img, index) => {
                    return <div style={{
                        animation: index === slider ? 'LeftToRight 3s infinite' : 'none'
                    }}></div>
                })}
            </div>
        </div>
    )
}

export default Carousel;