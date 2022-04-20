import React, { useState } from "react";
import { ICarouselIndicatorProps } from "../../../types/carousel";
import classNames from "classnames/bind";
import styles from './index.scss';

const cx = classNames.bind(styles);
const Index = (props: ICarouselIndicatorProps) => {

    const indicatorNums = Array.from({length:props.num},(x,i)=>i);
    
    return (
        <ul className={cx('carousselIndicatorContainer')}>

            {
                indicatorNums.map((currentIndex) => {
                    return (
                        <li>
                            <div className={cx('indicator-inner', currentIndex===props.selectIndex?'indicator-inner-select':null)}></div>
                        </li>
                    )
                })
            }

        </ul>
    )
}

export default Index;