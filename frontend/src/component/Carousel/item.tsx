import React from 'react';

export interface CarouselItemProps {
    id: string;
    title?: Array<string>;
    content?: Array<string>;
    image?: string;
    color?: string;
    bgColor?: string;
}

export const CarouselItem: React.FC<CarouselItemProps> = ({ title = [], content = [], image, color = '#000', bgColor = '#fff' }) => {

    return (
        <div className="carousel-item-container" style={{ backgroundColor: bgColor }}>
            <div className="carousel-item-info" style={{ color: color }}>
                {
                    title.map((text, index) => (
                        <div key={index} className="title">{text}</div>
                    ))
                }
                {
                    content.map((text, index) => (
                        <div key={index} className="content">{text}</div>
                    ))
                }
            </div>
            { image && <div className="carousel-item-img" style={{ backgroundImage: `url(${image})` }}></div> }
        </div>
    );
}