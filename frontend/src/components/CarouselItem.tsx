import React from "react";
import { FC } from "react";
import classes from './carousel.module.scss'
import { CarouselItemData } from "./interface";

export interface CarouselItemProps {
    data: CarouselItemData;
    width: string;
}


const CarouselItem: FC<CarouselItemProps> = (props) => {
    const {
        data,
        width,
        children,
    } = props;

    const { url, alt, title, desc, backgroundColor} = data

    return (
        <div className={classes.carousel_item} style={{ width, backgroundColor }}>
            <img src={url} alt={alt} style={{ backgroundColor }} />
            <div className={classes.content}>
                {title && <>{ title }</>}
                {desc && <>{ desc }</>}
            </div>
            {children}
        </div>
    )
}

export default CarouselItem;