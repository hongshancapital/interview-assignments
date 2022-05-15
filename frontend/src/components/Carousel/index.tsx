import React, { CSSProperties, useCallback, useEffect, useMemo, useRef, useState } from 'react';
import CarouselItem, { CarouselItemProps } from './CarouselItem';
import './index.scss';
import iphone from '../../assets/iphone.png';
import tablet from '../../assets/tablet.png';
import airpodsImg from '../../assets/airpods.png';
import Indicator from './Indicator';

export interface CarouselConfig {
    /**时间间隔 */
    interval: number;
    /**切换动画间隔 */
    speed: number;
    items: Array<CarouselItemProps>;
}

export default function Carousel() {
    const [curIndex, setCurIndex] = useState(0);
    const timer = useRef<any>();
    const config: CarouselConfig = useMemo(
        () => ({
            interval: 3000,
            speed: 500,
            items: [
                {
                    title: 'xPhone',
                    description: (<div>Lots to love. Less to spend.<br />Starting at $399</div>),
                    img: iphone,
                    style: {
                        color: '#fff',
                        backgroundColor: '#111',
                        backgroundSize:'50% auto',
                    },
                },
                {
                    title: 'Tablet',
                    description: 'Just the right amount of everything.',
                    img: tablet,
                    style: {
                        backgroundColor: '#fafafa',
                        backgroundSize: '100% auto',
                    },
                },
                {
                    title: (<div>Buy a Tablet or xPhone for colleage.<br /> Get airPods.</div>),
                    img: airpodsImg,
                    style: {
                        backgroundColor: '#f1f1f3',
                        backgroundSize: '125% auto',
                    },
                },
            ],
        }),
        []
    );
    /**播放 */
    const play = useCallback(
        () => {
            timer.current = setInterval(() => {
                setCurIndex((index) => (index + 1) % config.items.length);
            }, config.interval);
        },
        [config.interval, config.items.length]
    );
    /**停止 */
    const stop = useCallback(() => clearInterval(timer.current), []);
    const handleChange = useCallback((index) => {
        setCurIndex(index);
        stop();
        play();
    }, []);
    useEffect(() => {
        play();
        return () => {
            stop();
        };
    }, []);
    const sliderStyle: CSSProperties = {
        transform: `translateX(-${100 * curIndex}%)`,
        transition: `transform ${config.speed}ms linear`,
    };

    return (
        <div className='carousel'>
            <div className='carousel-container' style={sliderStyle}>
                {config.items.map((it, i) => <CarouselItem key={i} {...it} />)}
            </div>
            <div className='carousel-indicator'>
                <Indicator
                    total={config.items.length}
                    curIndex={curIndex}
                    interval={config.interval}
                    onChange={handleChange}
                />
            </div>
        </div>
    );
}
