import React, { useEffect, useState } from "react";
import { ICarouselProps } from "../../types/carousel";
import { LineIndicator } from "../carouselIndicator";
import classNames from "classnames/bind";
import styles from './index.scss';
import HsCarouselSlide from "../carouselSlide";

const cx = classNames.bind(styles);


const Carousel =(props:ICarouselProps)=>{
    
    const [selectIndex,setSelectIndex]=useState<number>(0)

    useEffect(()=>{
        setTimeout(()=>{
            setSelectIndex((selectIndex+1)%props.data.length)
        },props.delay ? props.delay :2000)
    })
    return(
        <div className={cx('carousselContainer')}>
            <HsCarouselSlide data={props.data} selectIndex={selectIndex}></HsCarouselSlide>
            {
                props.hasIndicator? props.indicator? props.indicator :(<LineIndicator num={props.data.length} selectIndex={selectIndex}/>) :null
            }
        </div>
    )
}

Carousel.defaultProps ={
    hasIndicator: true,
}
export default Carousel;