import React from 'react';

interface CarouselItemProps {
    className?: string;
    /** 当前索引 */
    index?: number;
    /** 宽度 */
    width?: number;
    children?: React.ReactNode;
}

const CarouselItem:React.FC<CarouselItemProps> = (props) => {
    const { 
        index = 0,
        width = window.screen.width,
        children,
        className } = props;
        
    return (
        <div className={`${className}`} key={index} style={{
            width: `${width}px`,
            height: "100%",
        }}>{children}</div>
    )
}

CarouselItem.displayName = 'CarouselItem'  

export default React.memo(CarouselItem) ;