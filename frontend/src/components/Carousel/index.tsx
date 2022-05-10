import React, {FC, ReactChild, useState, CSSProperties, memo} from "react";
import useRafInterval from '@/hooks/useRafInterval'
import './index.less'


export interface CustomCarouselItemProps {
    title: string;
    subTitle?: string;
    image: string;
    id?: number | string;
    style?: CSSProperties;
    className?: string;
}

export interface CarouselProps {
    duration?: number;
    speed?: number;
    style?: CSSProperties;
    className?: string;
    children?: ReactChild[];
}

// 轮播图内容
const CustomCarouselItem: FC<CustomCarouselItemProps> = ({title, subTitle, image, style, className = ''}) => {
    return (
        <div className={`custom-carousel-item ${className}`} style={style}>
            <div className="item-content">
                <h1>{title}</h1>
                {subTitle && <div className="sub-title">{subTitle}</div>}
            </div>
            <div className="img-container" style={{backgroundImage: `url(${image})`}}/>
        </div>
    );
};


const Carousel: FC<CarouselProps> = ({
    duration = 3000,
    speed = 300,
    style,
    className = '',
    children = []
}) => {
    const [current, setCurrent] = useState(0);

    useRafInterval(() => {
        setCurrent((prev) => (prev + 1) % children.length);
    }, duration);

    return (
        <div className={`carousel ${className}`} style={style}>
            <div
                className="carousel-list"
                style={{
                    width: `${100 * children.length}%`,
                    left: `-${100 * current}%`,
                    transition: `all ${speed}ms`
                }}
            >
                {children}
            </div>
            <ul className="dot-list">
                {
                    children.map((item, index: number) => ((
                        <li key={index} className='dot-item'>
                                <span
                                    className={current === index ? 'active' : ''}
                                    style={{animationDuration: `${duration}ms`}}
                                />
                        </li>
                    )))
                }
            </ul>
        </div>
    )
}

const TypedCarousel = memo(Carousel) as unknown as (FC<CarouselProps>) & {
    CustomItem: FC<CustomCarouselItemProps>
};
TypedCarousel.CustomItem = memo(CustomCarouselItem);

export default TypedCarousel;
