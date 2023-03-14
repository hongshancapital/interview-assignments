import React, { ReactElement, useEffect, useRef, useState } from 'react';
import './index.scss';

interface Props {
    duration: number;
    children: ReactElement[]
}

export default function Carousel(props: Props) {
    const [activeIndex, setActiveIndex] = useState<number>(0);
    const timer = useRef<number>();

    useEffect(() => {
        if (timer.current !== undefined) {
            clearTimeout(timer.current);
        }
        timer.current = window.setTimeout(refreshActiveIndex, props.duration * 1000)

        return () => window.clearTimeout(timer.current);
    }, [activeIndex])

    const refreshActiveIndex = () => {
        setActiveIndex((activeIndex + 1) % props.children.length);
    };

    return (
        <div className="carousel-container">
            <ul className="slider" style={{ left: -(activeIndex * 100) + '%' }}>
                {
                    props.children.map((child, index) => (<li className="item" key={`slider-${index}`}>
                        {child}
                    </li>))
                }
            </ul>
            <div className="time-bar">
                {
                    props.children.map((_, index) =>
                        <div className="bar-outer" key={`bar-${index}`} onClick={() => setActiveIndex(index)}>
                            <div className={'bar-inner ' + (activeIndex === index ? 'active' : '')}
                                 style={{ animationDuration: props.duration + 's' }}/>
                        </div>
                    )
                }
            </div>
        </div>
    );
}

