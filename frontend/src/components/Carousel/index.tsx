import './index.scss';
import React, { ReactElement, useCallback, useEffect, useRef, useState } from 'react';

interface Props {
    duration: number;
    children: ReactElement[]
}

export default function Carousel(props: Props) {
    const [activeIndex, setActiveIndex] = useState<number>(0);
    const wrapperEl = useRef<HTMLDivElement>(null);
    const sliderEl = useRef<HTMLDivElement>(null);
    const timer = useRef<number>();

    useEffect(() => {
        const refreshIndex = () => {
            setActiveIndex(0);
        };
        window.addEventListener('resize', refreshIndex);

        return () => {
            window.removeEventListener('resize', refreshIndex);
        }
    }, [])

    useEffect(() => {
        if (timer.current !== undefined) {
            clearTimeout(timer.current);
        }
        timer.current = window.setTimeout(refreshActiveIndex, props.duration * 1000)

        return () => window.clearTimeout(timer.current);
    }, [activeIndex])

    const getSliderWidth = useCallback(() => {
        if (wrapperEl.current) {
            return wrapperEl.current.getBoundingClientRect().width;
        }
        return 0;
    }, [wrapperEl.current]);

    const refreshActiveIndex = () => {
        if (sliderEl.current) {
            setActiveIndex((activeIndex + 1) % props.children.length);
        }
    };

    return (
        <div className="carousel-container" ref={wrapperEl}>
            <div className="slider" ref={sliderEl} style={{ left: -(activeIndex * getSliderWidth()) + 'px' }}>
                {
                    props.children.map((child, index) => (<div className="item" key={`slider-${index}`}>
                        {child}
                    </div>))
                }
            </div>
            <div className="time-bar">
                {
                    props.children.map((_, index) =>
                        <div className="bar-outer" key={`bar-${index}`}>
                            <div className={'bar-inner ' + (activeIndex === index ? 'active' : '')}
                                 style={{ animationDuration: props.duration + 's' }}/>
                        </div>
                    )
                }
            </div>
        </div>
    );
}

