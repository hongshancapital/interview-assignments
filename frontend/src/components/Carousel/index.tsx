import React, { useEffect, useRef, useState, useCallback } from "react";
import Pagination from "./pagination";
import { PRE_CLS } from "../constant"
import { CaruoselPropsType } from "./PropTypes";

const prefixCls = PRE_CLS + "-carousel";

const Carousel = (props: CaruoselPropsType) => {
    const {
        data,
        playTiming = 3000,
        duration = 300,
        isLinkJump = false,
        itemStyle,
        container = ".carousel-container",
        itemImageClass,
        afterClickFn,
        panigationProps,
        ...restProps
    } = props;

    let timerInterval = useRef<NodeJS.Timer>();
    const swiperNode = document.querySelector(container);
    let containerWidth = swiperNode?.clientWidth || 0;

    let [hasData, setHasData] = useState(false); //是否重新开始
    let [activeIndex, setActiveIndex] = useState(0); //当前帧的序号，默认第一个
    let [leftDistance, setLeftDistance] = useState(containerWidth);
    let [swiDuration, setSwiDuration] = useState(duration);

    let currentIndex = useRef(activeIndex)

    const onHandleClick = function () {
        if (afterClickFn) afterClickFn(activeIndex);
    }
    const paginationClick = function (index: number) {
        setActiveIndex(index);
    }
    const initScroll = useCallback(() => {
        if (!hasData) return

        setActiveIndex(c => c + 1)

        timerInterval.current = setInterval(() => {
            if (currentIndex.current === data.length) {
                setTimeout(() => {
                    setSwiDuration(0);
                    setLeftDistance(0)
                    setActiveIndex(0);
                }, duration)

                setTimeout(() => {
                    setSwiDuration(duration);
                    setLeftDistance(containerWidth);
                    setActiveIndex(c => c + 1);

                }, duration + 200);
            } else {
                setActiveIndex(c => c + 1)
                setLeftDistance((currentIndex.current + 1) * (containerWidth as number))
            }

        }, playTiming)

    }, [hasData, playTiming, duration, containerWidth, data])


    useEffect(() => {
        initScroll();
    }, [initScroll])

    useEffect(() => {
        currentIndex.current = activeIndex;
    }, [activeIndex])

    useEffect(() => {
        if (Array.isArray(data) && data.length) {
            setHasData(true);
        } else {
            setHasData(false);
        }

    }, [data])

    useEffect(() => {
        if (containerWidth) {
            setLeftDistance(containerWidth);
            setSwiDuration(duration);

        }
    }, [containerWidth, duration]);


    return (
        <>
            {!hasData && <></>}
            {hasData &&
                <div className={prefixCls} {...restProps}>
                    <div className={`${prefixCls}-cover`}
                        style={{
                            transform: `translate3d(-${leftDistance}px, 0, 0)`,
                            transitionDuration: `${swiDuration}ms`,
                        }}
                    >
                        {isLinkJump && (
                            <>
                                <a key={`${prefixCls}-00`} className={`${prefixCls}-slide-item`} style={{ ...itemStyle }} href={data[0]['clickUrl']}>
                                    <img src={data[data.length - 1]['imgUrl']} alt={data[data.length - 1]['altInfo']} className={itemImageClass} />
                                </a>
                                {
                                    data && data.map((item, index) => {
                                        return (
                                            <a key={`${prefixCls}-${index}`} className={`${prefixCls}-slide-item`} style={{ ...itemStyle }} href={item.clickUrl}>
                                                <img src={item.imgUrl} alt={item.altInfo} className={itemImageClass} />
                                            </a>
                                        )
                                    })
                                }

                            </>
                        )}
                        {!isLinkJump && (
                            <>
                                <div role="test" key={`${prefixCls}-00`} className={`${prefixCls}-slide-item`} style={{ ...itemStyle }} onClick={onHandleClick}>
                                    <img src={data[data.length - 1]['imgUrl']} alt={data[data.length - 1]['altInfo']} className={itemImageClass} />
                                </div>
                                {
                                    data && data.map((item, index) => {
                                        return (
                                            <div key={`${prefixCls}-${index}`} className={`${prefixCls}-slide-item`} style={{ ...itemStyle }} onClick={onHandleClick}>
                                                <img src={item.imgUrl} alt={item.altInfo} className={itemImageClass} />
                                            </div>
                                        )
                                    })
                                }

                            </>
                        )}
                    </div>
                    <Pagination
                        activeIndex={currentIndex.current}
                        etiming={playTiming}
                        size={data.length}
                        onClickFn={(index: number) => {
                            paginationClick(index)
                        }}
                        {...panigationProps}
                    />
                </div>
            }
        </>
    )
}



export default Carousel;