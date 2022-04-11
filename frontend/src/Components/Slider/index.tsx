import React, { FunctionComponent, useState, useEffect, useMemo } from 'react';
import './index.scss';
import Progress from "../Progress";

interface SliderProps {
    duration?: number;
    speed?: number;
    sliders: JSX.Element[];
    showProgress?: boolean;
}


const Slider: FunctionComponent<SliderProps> = ({
    duration = 3000,
    speed = 200,
    sliders,
    showProgress = true
}) => {
    let timer: NodeJS.Timeout | null = null;
    const total = sliders.length;
    const [curIndex, setCurIndex] = useState(0);

    useEffect(() => {
        const autoPlay = () => {
            timer = setInterval(() => {
                setCurIndex(i => (i + 1) % total);
            }, duration);
        }
        const clearTimer = () => {
            timer && clearInterval(timer);
        };

        autoPlay();
        return clearTimer;
    }, [duration, total]);

    const transformStyle = useMemo(() => {
        return ({
            width: `${total * 100}%`,
            transform: `translateX(${(-curIndex / total) * 100}%)`,
            transitionDuration: `${speed}ms`
        });
    }, [total, speed, curIndex]);

    const progressDom = () => {
        const sliderDom: JSX.Element[] = sliders.map((item, index) => {
            return <Progress active={index === curIndex} duration={duration} key={index} />
        });
        return showProgress ? <div className='progress-container'>{sliderDom}</div> : '';
    }

    return (
        <div className="carouse-wrapper">
            <div className="slider-container" style={transformStyle}>
                {sliders.map((item, index) => {
                    return <div className="slider" key={index}> {item}</div>;
                })}
            </div>
            {progressDom()}
        </div >
    );
}

export default Slider;