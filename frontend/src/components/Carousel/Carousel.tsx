/**
 * @FileDescription: 轮播图通用组件
 * @Params: {
 *      carouselList: [
 *          {
 *              title?: ['一层标题', '二层标题'...],
 *              description?: ['一层描述', '二层描述'],
 *              imageURL: 背景图地址
 *          }
 *      ],
 *      intervalTime?: 轮播间隔时间（包含切换时间（0.5s）和 停留时间），默认 3000，单位毫秒,
 *      height?: 组件高度，单位px，默认撑满父级容器的高,
 *      width?: 组件宽度，单位px，默认撑满父级容器的宽,
 * }
 * @Functions: 自动轮播，鼠标移入移出暂停启动，进度条点击图片跳转
 * @Author: templechen
 * @Email: templechen@126.com
 * @Date: 2023-04-06
 * @LastEditors: templechen
 * @LastEditTime: 2023-04-06
 */
import React, { useState, useEffect, useRef } from "react";
import { CarouselProps, CarouselStyle, CarouselItem } from "./Carousel.d";

import './Carousel.css'

const Carousel: React.FC<CarouselProps> = ({ carouselList, intervalTime = 3000, height, width }) => {
    // 轮播组件宽高样式
    const [carouselStyle, setCarouselStyle] = useState<CarouselStyle>({});
    // 当前轮播图片下标
    const [currentImageIndex, setCurrentImageIndex] = useState<number>(0);
    // 轮播启动标识,用于页面上进度条停止判断
    const [intervalIDState, setIntervalIDState] = useState<NodeJS.Timer | undefined>(undefined);
    // 轮播启动标识,用于定时器启动销毁逻辑判断
    const intervalID: React.MutableRefObject<NodeJS.Timer | undefined> = useRef(undefined);

    useEffect(() => {
        handleCarouselStyle();
        carouselStart();

        return () => {
            carouselStop();
        };
    }, [carouselList, intervalTime, height, width]);

    // 轮播启动
    const carouselStart = () => {
        carouselStop();

        intervalID.current = setInterval(() => {
            setCurrentImageIndex(preValue => {
                if (preValue >= carouselList.length - 1) {
                    return 0;
                }
                return preValue + 1;
            });
        }, intervalTime);
        setIntervalIDState(intervalID.current);       
    };

    // 轮播停止
    const carouselStop = () => {
        if (intervalID.current) {
            clearInterval(intervalID.current);
            intervalID.current = undefined;
            setIntervalIDState(undefined);
        }
    };

    // 处理轮播图宽高样式
    const handleCarouselStyle = () => {
        if (height || width) {
            let carouselStyleTemp: CarouselStyle = {};
            height && (carouselStyleTemp.height = height + 'px');
            width && (carouselStyleTemp.width = width + 'px');
            setCarouselStyle(carouselStyleTemp);
        }
    };

    return (
        <div className="carousel" style={carouselStyle} onMouseEnter={ carouselStop } onMouseLeave={ carouselStart }>
            {/* 轮播图组 */}
            <div className="carousel-images">
                { carouselList.map((carouselItem: CarouselItem, index: number) => (
                    <img
                        className="carousel-image"
                        key={ index }
                        src={ carouselItem.imageURL }
                        style={{ transform: `translateX(-${ currentImageIndex * 100 }%)` }}
                    />
                )) }
            </div>
            {/* 滚动条 */}
            <div className="carousel-bar">
                { carouselList.map((carouselItem: CarouselItem, index: number) => (
                    <div className="carousel-bar-item-out" key={ index } onClick={ () => setCurrentImageIndex(index) } style={{ width: `${ (100 / carouselList.length).toFixed(0) }%` }}>
                        {/* 动效处理 */}
                        <div
                            className={`carousel-bar-item-in ${ !intervalIDState ? 'carousel-bar-item-stop' : ''}`}
                            style={ currentImageIndex === index ? (intervalIDState ? { animation: `aniBar ${ (intervalTime / 1000).toFixed(1) }s linear` } : { width: '100%' }) : {} }
                        ></div>
                    </div>
                )) }
            </div>
        </div>
    );
};

export default Carousel;