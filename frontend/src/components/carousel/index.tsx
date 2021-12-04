import React, {useState, useEffect } from 'react';
import classnames from "classnames";
import './index.scss';

type Props = {
    duration?: number,
    children: JSX.Element | JSX.Element[];
};
// 进度条
const transitionDiv = (duration: number) => {
    const buttonElement = document.getElementsByClassName("button")
    for (let i = 0; i< buttonElement.length; i++ ) {
        if (buttonElement[i]) {
            (buttonElement[i] as HTMLElement).style.transition = `width ${duration}s linear`
        }
    }
}
// 切换画面
const CarouselReal = (duration: number, children: JSX.Element[]) => {
    const length = children.length
    // 声明一个叫 "index" 的 state 变量
    const [index, setIndex] = useState(-1);
    // Similar to componentDidMount and componentDidUpdate:
    useEffect(() => {
        let dom = (document.getElementById("main-content") as HTMLElement)
        // transition: left 1s;
        dom.style.transition = `left ${duration / 4}s`
        if (index === -1) { // 为了让第0个元素产生变化
            setIndex(0);
        } else {
            setTimeout(() => {
                setIndex((index + 1) % length)
                dom.style.left = `${-((index + 1) % length) * document.documentElement.clientWidth}px`
            }, duration * 1000)
        }

    });
    const liList = []
    for(let i = 0; i < length; i++ ){
        liList.push(<li key={i} className={classnames({'active': index === i})}><div><div className="button" /></div></li>)
    }
    transitionDiv(duration)
    return (
        <div className="box">
            <div className="main-content" id="main-content">
                {children}
            </div>
            <div className="multi-circles">
                <ul>
                    {liList}
                </ul>
            </div>
        </div>
    );
}
// 函数组件
const Carousel = ({ duration = 3, children }: Props) => {
    if (children instanceof Array) {
        return CarouselReal(duration, children as JSX.Element[])
    } else {
        return (
            <div className="box">
                <div className="main-content" id="main-content">
                    {children}
                </div>
            </div>
        );
    }
}

export default Carousel;