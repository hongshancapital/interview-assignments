import React, { useEffect, useState } from 'react';
import { CarouselItem, CarouselItemProps } from './item';
import './index.css';

export interface CarouselProps {
    interval?: number;
    items: Array<CarouselItemProps>
}

export const Carousel: React.FC<CarouselProps> = ({ interval = 2000, items = [] }) => {
    const [activeIndex, setActiveIndex] = useState<number>(0);
    let timer: NodeJS.Timeout;

    const handleClick = (index: number) => {
        clearTimeout(timer);
        setActiveIndex(index);
    }

    useEffect(() => {
        timer = setTimeout(() => {
            setActiveIndex(index => {
                return index === items.length - 1 ? 0 : index + 1;
            });
        }, interval);
        return () => {
            clearTimeout(timer);
        };
    }, [activeIndex]);

    return (
        <div className="carousel-container">
            <div className="carousel-content" style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
                {
                    items.map((item: CarouselItemProps) => {
                        const { id, title, content, image, color, bgColor } = item;
                        return (
                            <CarouselItem
                                key={id}
                                id={id}
                                title={title}
                                content={content}
                                image={image}
                                color={color}
                                bgColor={bgColor}
                            />
                        );
                    })
                }
            </div>
            <div className="indicator-container">
                {
                    items.map((item, index) => {
                        return (
                            <div key={item.id} className="indicator-item" onClick={() => handleClick(index)}>
                                <div className={`slider ${activeIndex === index ? 'active' : ''}`} style={{animationDuration: `${interval}ms`}}></div>
                            </div>
                        );
                    })
                }
            </div>
        </div>
    );
}