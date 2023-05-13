import React, { FC } from "react";
import { BaseComponentProps, classNames } from "../../";
import './index.css';

export interface CarouselContentProps extends BaseComponentProps {
    /** 渲染索引 */
    renderIndex: number,
}

/**
 * 跑马灯基础组件--内容框
 * @param param0 
 * @returns 
 */
export const CarouselContent: FC<CarouselContentProps> = ({
    className,
    style,
    renderIndex,
    children
}) => { 

    const contentStyle: React.CSSProperties = {
        ...style,
        left: `${renderIndex * 100}%`,
    }

    return <div 
        key = {`carousel-content-${renderIndex}`}
        className={classNames('carousel-image-page', className)} 
        style={contentStyle}
    >
        { children }
    </div>
}
