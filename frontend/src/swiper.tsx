import React, { useState, useEffect, useRef } from "react";
import './swiper.css'

interface SlideProps {
    images: object[];
    interval?: number;
    width?: string;
    height?: string;
    children?: React.ReactNode;
}

const Carousel: React.FC<SlideProps> = ({
                                         images,
                                         interval = 3000,
                                         width = '800px',
                                         height = '400px',
                                         children = null,
                                     }) => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [isSliding, setIsSliding] = useState(false);
    const slideIntervalRef = useRef<number>();

    useEffect(() => {
        if (isSliding) {
            slideIntervalRef.current = window.setTimeout(() => {
                setCurrentIndex((currentIndex + 1) % images.length);
            }, 3000);
        }
        return () => {
            window.clearTimeout(slideIntervalRef.current);
        };
    }, [currentIndex, isSliding, images.length]);

    useEffect(() => {
        const handleResize = () => {
            const slideWrapper = document.getElementById("slide-wrapper");
            if (slideWrapper) {
                slideWrapper.style.height = `${height}`;
                slideWrapper.style.width = `${width}`;
            }
        };
        handleResize();
        window.addEventListener("resize", handleResize);
        return () => {
            window.removeEventListener("resize", handleResize);
        };
    }, [width, height]);

    useEffect(() => {
        setIsSliding(true);
    }, []);

    useEffect(() => {
        if (isSliding) {
            slideIntervalRef.current = window.setInterval(() => {
                setCurrentIndex((currentIndex + 1) % images.length);
            }, interval);
        }
        return () => {
            window.clearInterval(slideIntervalRef.current);
        };
    }, [currentIndex, isSliding, images.length, interval]);

    useEffect(() => {
        const slideList = document.getElementById("slide-list");
        if (slideList) {
            slideList.style.transform = `translateX(-${currentIndex * 100}%)`;
        }
    }, [currentIndex]);

    return (
        <div style={{position: 'relative', height:'100%', width: '100%', overflow: 'hidden'}} id="slide-wrapper">
            <div className={'swiper-wrapper'} id="slide-list">
                {
                    children
                    ?
                        children
                        :
                        images.map((image: any, index) => (
                                <div key={index}>
                                    <img src={image.img} alt={`slide-${index}`} />
                                </div>
                            ))
                }
            </div>
            <Progress elements={children} time={3000} currentIndex={currentIndex}/>
        </div>
    );
};

interface Progress {
    time?: any;
    elements?: any;
    currentIndex?: number;
}

const Progress: React.FC<Progress> = ({elements, time, currentIndex}) => {
    const [width, setWidth] = useState('0%')
    useEffect(() => {
        setWidth('0%')
         const timer = setInterval(() => {
                setWidth((preWidth) => {
                    const newWidth = parseFloat(preWidth) + 100 / Math.floor(time / 10)

                    return newWidth >= 100 ? '0%' : `${newWidth}%`
                })
            }, 10)
        if (width === '100%') {
            setWidth('0%')
            clearInterval(timer)
        }
        return () => {
            clearInterval(timer)
        }
    }, [currentIndex])

    return (
        <div className={'progress-wrapper'}>
            {elements.map((_: any, index: number) => {
                return (
                    <div key={index} className={'progress'}>
                        <span style={{width: currentIndex === index ? width : '0%'}}></span>
                    </div>
                )
            })}
        </div>
    )
}

export default Carousel;