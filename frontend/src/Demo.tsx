import React, { useEffect, useState } from "react";
import Carousel from "./components/Carousel";
import "./Demo.css";

const iphone = require("./assets/iphone.png");
const tablet = require("./assets/tablet.png");
const airpods = require("./assets/airpods.png")

function getWindowSize() {
    const { innerWidth, innerHeight } = window;
    return { innerWidth, innerHeight };
}

function Demo() {
    const [dimensions, setDimensions] = useState(getWindowSize().innerHeight);
    useEffect(() => {
        const handleResize = () => {
            setDimensions(getWindowSize().innerHeight);
        };
        window.addEventListener('resize', handleResize);
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    const itemStyle: React.CSSProperties = {
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        height: dimensions,
        backgroundSize: 'auto 100%'
    }
    const itemList = [
        {
            title: 'xPhone',
            context: 'Lots to love.Less to spend.\nstarting at $399.',
            img: iphone,
            style: { color: '#fff', background: `url(${iphone})`,backgroundColor: '#111', ...itemStyle }
        },
        {
            title: 'Tablet',
            context: 'Just the right amount of everything.',
            img: tablet,
            style: { color: '#000', background: `url(${tablet})`, ...itemStyle }
        },
        {
            title: 'Buy a Tablet or xPhone for college.\nGet airPods.',
            context: '',
            img: airpods,
            style: { color: '#000', background: `url(${airpods})`, ...itemStyle }
        }
    ]

    return <>
        <Carousel autoplay>
            {
                itemList.map(item => (
                    <div className="carousel-item" key={item.title} style={item.style}>
                        <h3>{item.title}</h3>
                        <p>{item.context}</p>
                    </div>
                ))
            }
        </Carousel>
    </>;
}

export default Demo;
