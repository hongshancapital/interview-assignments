import React, {CSSProperties, useEffect, useState} from "react";
import "./index.css";

//图片信息
export interface ImgInfo {
    key: string,
    //图片地址
    imgUrl: string,
    alt?: string,
    //图片文字描述
    label: string[],
    note?: string[],
    color?: string
}

//轮播属性
export interface CarouselProps {
    //每幅轮播持续时间
    duration?: number,
    //切换时间
    swapTime?: number,
    //图片信息
    items: ImgInfo[];
}

export default function Carousel(props: CarouselProps) {
    const {
        duration = 3000,
        swapTime = 500,
        items = [],
    } = props;
    //当前轮播图的索引
    const [activeIndex, setActiveIndex] = useState(0);

    useEffect(() => {
        let count = 0;
        //轮播切换
        let intval1 = setInterval(() => {
            count = ++count % items.length;

            setActiveIndex(count);
        }, duration);
        return () => {
            clearInterval(intval1);
        };
    }, [duration, items.length]);

    return (
        <div className="carousel">
            <div
                className="carousel-container"
                data-testid="carousel-container"
                style={{
                    transitionDuration: swapTime +"ms",
                    transform: `translateX(-${activeIndex * 100}%)`,
                }}
            >
                {items.map((item, index) => {
                    return (
                        <div key={index} className="carousel-item">
                            <div className="banner">
                                <img
                                    className="banner-img"
                                    src={item.imgUrl}
                                    alt={item.alt}
                                />
                                <div className="banner-title" style={{ color: item.color }}>
                                    {/*banner标题部分*/}
                                    {item.label ?
                                        item.label.map((itemInfo, idx) => {
                                            return <div key={'title-' + idx} className="banner-label">{itemInfo}</div>;
                                        })
                                        : ''
                                    }
                                    {/*备注*/}
                                    {item.note ?
                                        item.note.map((itemInfo, idx) => {
                                          return <div key={`note` + idx} className="banner-note">{itemInfo}</div>
                                        })
                                        : ''
                                    }
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
            {/* 轮播条 */}
            <div className="carousel-step">
                {items.map((item, index) => {
                    return (
                        <div
                            className={`carousel-step-item ${
                                index === activeIndex ? "active" : ""
                            }`}
                            key={item.key}
                            data-testid={item.key}
                        >
                            <div key={item.key + `-bg`} className="carousel-step-item-bg" style={{ animationDuration: duration + "ms" }}></div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}
