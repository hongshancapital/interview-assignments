import React, { FC, CSSProperties } from "react";
import styles from "./style.module.scss";

interface CarouselItemProps {
    className?:string,
    style?:CSSProperties,
    children?:unknown
}
const CarouselItem : FC<CarouselItemProps> = ({className, style, children}) =>{

    return(
        <div className={`${styles["carousel-item"]} ${className}`} style={style} data-testid="test-carousel-item-id">{children}</div>
    )
};
export default CarouselItem;