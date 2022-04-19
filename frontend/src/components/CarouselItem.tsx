import React, { ReactNode } from "react";
import { CSSProperties, FC } from "react";
import classes from './carousel.module.scss'

export interface CarouselItemProps {
    url: string;
    style?: CSSProperties;
    title: ReactNode;
    desc: ReactNode;
    alt: string;
}


const CarouselItem: FC<CarouselItemProps> = (props) => {
    const {
        url,
        alt,
        style,
        title,
        desc,
        children,
    } = props;

    return (
        <div className={classes.carousel_item} style={style}>
            <div className={classes.content}>
                {title && <>{ title }</>}
                {desc && <>{ desc }</>}
            </div>
            { children }
            <img src={url} style={style} alt={ alt }/>
        </div>
    )
}

export default CarouselItem;