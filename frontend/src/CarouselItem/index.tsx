import React, { CSSProperties, HTMLAttributes, ReactNode } from 'react';
import './index.css';

interface CarouselItemProps {
    title: ReactNode;
    description: ReactNode;
    icon?: string;
    style?: CSSProperties;
}

const CarouselItem: React.FC<CarouselItemProps> = props => {

    const{ title, description, ...rest } = props;

    return (
        <div className="carousel-item" {...rest}>
            <div>
                <div className="carousel-item-title">{title}</div>
                <div className="carousel-item-description">{description}</div>
            </div>
        </div>
    )
}

CarouselItem.displayName = 'CarouselItem';

export default CarouselItem;
