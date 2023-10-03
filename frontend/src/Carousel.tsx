import React, { FC, useEffect, useState } from 'react';
import './carousel.scss';
export interface CarouselProps {
    list: CarouselItemProps[];
    timer?: number; // 毫秒
}
export interface CarouselItemProps {
    title: string | React.ReactNode;
    description: string | React.ReactNode;
    image: any;
    key: any;
    textClassName?: string;
}
let timerTranslate: any = null;
const Carousel: FC<CarouselProps> = ({ list, timer = 5000 }) => {
    const [index, setIndex] = useState<number>(0);
    const loop = () => {
        timerTranslate = setTimeout(() => {
            if (index === list.length - 1) {
                setIndex(0);
            } else {
                setIndex(index + 1);
            }
            loop();
        }, timer);
    };
    useEffect(() => {
        loop();
        return () => {
            clearTimeout(timerTranslate);
        };
    });
    return (
        <div className={`carousel`}>
            {list.map((item: CarouselItemProps, listIndex: number) => (
                <div
                    key={item.key}
                    className={`item ${index === listIndex ? 'active' : ''}`}
                >
                    <div className={`des ${item.textClassName}`}>
                        <div className="title">{item.title}</div>
                        <div>{item.description}</div>
                    </div>

                    <img src={item.image} className="img" alt=""></img>
                </div>
            ))}
            <div className="pagination">
                {
                   list.map((item: CarouselItemProps, listIndex: number) => <span key={item.key} className={`pagination-item ${index === listIndex ? 'pagination-active': ''}`}>
                    <span className='dom'></span>
                   </span>) 
                }
            </div>
        </div>
    );
};

export default Carousel;
