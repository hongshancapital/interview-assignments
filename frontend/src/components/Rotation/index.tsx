import React, { useEffect, useState } from "react";
import './index.scss';

interface PropsData {
    imgs: string[],
    duration?: number
}

export default function Rotation(props: PropsData) {
    const [activeIndex, setActive] = useState<number>(0);
    const [leftIndex, setLeftIndex] = useState<number>(0);
    const [currentWinth, setCurrentWinth] = useState<number>(0);
    const duration = props.duration || 2000

    useEffect(() => {
        setTime(1)
        getDomWidth();
    }, [])

    const setTime = (index: number) => {
        let timer = setInterval(() => {
            if (index >= props.imgs.length - 1) {
                clearInterval(timer);
                setTime(0)
            }
            const currentIndex = index;
            setActive(currentIndex);
            setLeftIndex(currentIndex)
            index++;
        }, props.duration)
    }

    const getDomWidth = () => {
        const dom = document.querySelector('.rotation-box') as HTMLBaseElement;
        const width = dom.clientWidth;
        setCurrentWinth(width);
    }
    

    return (<div className="rotation-box">
        <div className="rotation-list" style={{width: 100*props.imgs.length + '%', transform: `translateX(-${currentWinth*leftIndex}px)`, transition: `all ${duration - 1500}ms`}}>
            {
                // eslint-disable-next-line jsx-a11y/alt-text
                props.imgs.map((item, index) => <img className="img-box" style={{width: 100/props.imgs.length + '%'}} src={item} key={index} />)
            }
        </div>
        <div className="rotation-spot">
            {
                [1, 2, 3].map((_item, index) => <div key={index} className={`item ${activeIndex === index ? 'active' : ''}`}></div>)
            }
            
        </div>
    </div>)
}