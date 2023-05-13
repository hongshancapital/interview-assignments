import React, { ReactNode, FC } from "react"
import { BaseComponentProps } from "../../"
import { CarouselContentProps, CarouselContent } from "../carouselContent"
import './index.css';

export interface ImageProps extends BaseComponentProps {
    image: string,
    title?: ReactNode[],
    description?: ReactNode[],
}

export interface CarouselImageProps extends Partial<ImageProps> {
    renderIndex: number,
}

/**
 * 跑马灯基础组件--图片
 * @param param0 
 * @returns 
 */
export const CarouselImage: FC<CarouselImageProps> = ({
    className,
    style,
    image, 
    description, 
    title,
    renderIndex,
}) => {
    
    const carouselContentProps: CarouselContentProps = {
        className,
        renderIndex,
        style: {
            ...style,
            backgroundImage: `url(${image})`
        }   
    }
    return <React.Fragment>
        <CarouselContent {...carouselContentProps}>

            {/* @todo 媒体适配 */}
            <div className='carousel-image-slogn'>

                {/* 标题渲染 */}
                { title?.map(item =>  <h1>{item}</h1>) }

                {/* 描述渲染 */}
                { description?.map(item => <span>{item}</span>) }
            </div>
        </CarouselContent>
    </React.Fragment>
}
