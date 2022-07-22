/**
* @decsription 不使用常规定时器实现自动轮播组件
* @author xiaoshihui 
* @Date 2022-07-19
* @extend 待扩展项-无缝轮播（添加副本），左右按钮切换
* @other 利用下方进度条animation做无限循环动画播放,监听onAnimationEnd做图片轮播
*/

import React, { useState, useEffect, useRef, useCallback } from 'react';
import './index.css';

export interface Items {
    key: number;
    title: string[];
    subTitle?: string[];
    backgroundColor?: string;
    color?: string;
    image: string;
}

export interface Iprops {
    config: Items[];
	autoPlay?: Boolean;
	autoPlayTime?: number;
	durationTime?: number;
}

const Carousel = (props: Iprops) => {
    const { config, autoPlayTime = 3000, durationTime = 500 } = props;
    const [offset, setOffset] = useState<number>(0);
    const [current, setCurrent] = useState<number>(0);
    const carouselRef = useRef<HTMLDivElement>(null);
    // const ref = useRef({ active: 0 }); // 解决了在调用next函数及它里面的current值没更新的问题

    // 利用轮播进度条动画，监听onAnimationEnd做循环播放
    const autoPlay = useCallback(() => {
        if (current >= config.length - 1) {
            setCurrent(0);
        } else {
            setCurrent(current + 1);
        }
    }, [current, config]);

    const getOffsetWidth = () => carouselRef.current?.offsetWidth || 0;

    useEffect(() => {// 监听浏览器收缩适配
        setOffset(getOffsetWidth());
        window.addEventListener('resize', () => {
            setOffset(getOffsetWidth());
        });
        return () => {
            window.removeEventListener("resize", () => {
                setOffset(getOffsetWidth());
            });
        }
    }, []);

    // 内容配置项
    const renderItem = () => (
        config.map((item: Items) => (
            <li className="carousel-item">
                <div className="content" key={item.key} style={{ backgroundColor: item.backgroundColor, color: item.color }}>
                    <div className="content-wraper">
                        {
                            item.title?.map((title: String, i: number) => (
                                <p key={i}>{title}</p>
                            ))
                        }
                        {
                            item.subTitle?.map((title: String, i: number) => (
                                <div key={i} className="subtitle">{title}</div>
                            ))
                        }
                            <img className="img" src={item.image} alt="图片" />
                    </div>
                </div>
            </li>
        ))
    )

    return (
        <div className="carousel" ref={carouselRef}>
            <div
                className="carousel-box"
                style={{
                    transform: `translateX(-${current * offset}px)`,
                    transitionDuration: `${durationTime}ms`
                }}
            >
                <ul
                    className="carousel-wraper"
                >
                    {renderItem()}
                </ul>
            </div>
            <div className="bar">
                {config?.map((item: Items, index: number) => {
                    return (
                        <div
                            className="bar-progress"
                            key={index}
                            onClick={autoPlay}
                        >
                            <div
                                onAnimationEnd={autoPlay}
                                className="bar-progress-inner"
                                style={current === index ? { animation: `mymove ${autoPlayTime}ms linear`,} : undefined}
                            ></div>
                        </div>
                    )
                })}
            </div>
        </div>
    );
}

export default Carousel;
