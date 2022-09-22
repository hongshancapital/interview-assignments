import React, { useState, useEffect, useRef } from 'react';

import { CarourselProps, CarourselRef } from './types';
import CarourselItem from './item';
import './style.css';
import { IBaseInf } from '../../types/modelTypes';

const Caroursel = ({
    //起始帧
    startIndex = 0,
    // 切换速度
    switchSpeed = 500,
    // 切换间隔
    switchInterval = 3000,
    // 点击事件
    onClick,
    // 切换帧事件
    onChange,
    // 数据
    data = []
}: CarourselProps) => {

    const [current, setCurrent] = useState(startIndex);
    const commonRef = useRef<CarourselRef>({});
    const len = data.length;

    // 切换帧事件
    const switchEvt = (index: undefined | number) => {
        if (index === undefined) {
            // 自动模式，计算当前值
            index = current + 1;
        } else if (index === current) {
            // 手动设置index模式时，如果和当前帧一样，则不做动作；
            return;
        } else {
            // 手动设置index模式时，清除动画
            commonRef.current.timer && clearTimeout(commonRef.current.timer);
        }

        commonRef.current.timer = undefined;

        setCurrent(index % len);
        onChange && onChange(data[current], current);
    };

    // 点击事件
    const clickEvt = () => {
        let result: boolean | void,
            itemData: IBaseInf = data[current];

        // 处理传入的点击事件，拿到结果进行判断
        onClick && (result = onClick(data[current], current));

        // 返回false，则不执行默认动作
        if (result === false) {
            return;
        }

        // 如果存在链接，则跳转页面
        itemData.link && window.open(itemData.link);
    }

    useEffect(() => {
        // 开启定时轮换
        commonRef.current.timer = window.setTimeout(switchEvt, switchInterval);
    }, [current]); //eslint-disable-line 
    //避免依赖报错，这里不用依赖switchEvt和switchInterval

    return (

        <div className='carousel' >
            <div className='carousel-container' onClick={clickEvt}
                style={{ transform: `translateX(-${current * 100}%)`, transitionDuration: `${switchSpeed}ms` }}>
                {data.map((itemData) => <CarourselItem key={itemData.id} {...itemData}></CarourselItem>)}
            </div>
            <div className='carousel-indicators'>
                {data.map((itemData, i) => {
                    return <div key={i} className='carousel-indicator' onClick={() => switchEvt(i)}>
                        <div className={current === i ? 'carousel-indicator-active' : ''}
                            style={{ animationDuration: `${switchInterval}ms` }} />
                    </div>
                })}
            </div>
        </div>
    )
}

export default Caroursel;