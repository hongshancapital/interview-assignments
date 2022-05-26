import React from 'react';
import './index.scss';

export interface CarouselItemProps {
    /**标题 */
    title: string | JSX.Element;
    /**描述 */
    description?: string | JSX.Element;
    /**图片 */
    img: string;
    /**图片跳转链接 */
    link?: string;
    style?: React.CSSProperties;
}

export default function CarouselItem(props: CarouselItemProps) {
    return (
        <div className='carousel-item' style={{ backgroundImage: `url(${props.img})`, ...props.style }}>
            {props.title && <h1 className='carousel-item-header'>
                <a href={props.link} target="_blank">{props.title}</a>
            </h1>}
            {props.description && <div className='carousel-item-description'>{props.description}</div>}
        </div>
    );
}
