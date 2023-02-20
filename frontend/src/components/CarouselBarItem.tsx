import React from "react";
import { FC } from "react";
import style from './carousel.module.scss';
import classNames from "classnames";

export interface CarouselBarItemProps {
    animationDuration: number; // 动画时间，单位 秒（s）
    animationStart: boolean;
}


const CarouselBarItem: FC<CarouselBarItemProps> = (props) => {
    const { animationDuration, animationStart } = props;

    return (
        <div className={style.bar_item}>
            <span
                className={classNames({
                    [style.bar_item_inner]: true,
                    [style.bar_item_inner_ani]: animationStart
                })}
                style={{ animationDuration: `${animationDuration}s`}}
            >
            </span>
        </div>
    )
}

export default CarouselBarItem;