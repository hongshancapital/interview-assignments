import React, { FC } from "react";
import classNames from "classnames";
import './index.css';


export interface ICarouselDotProps {
    duration: number;
    isActive: boolean
}

const CarouselDot: FC<ICarouselDotProps> = ({ duration, isActive }) => {
    const commonCls = 'carousel-dot';
    
    return (
        <div className={classNames(commonCls)}>
            <div
                style={{ 
                    width: isActive ? '100%' : 0,
                    transition: isActive ? `width ${duration / 1000}s ease` : 'none'
                }}
                className={`${commonCls}-progress`}>
            </div>
        </div>
    )
}

export default CarouselDot;