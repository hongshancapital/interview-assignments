import "./index.css";

import React, {
    CSSProperties,
    memo,
    useCallback,
    useEffect,
    useMemo,
    useRef,
    useState,
} from "react";

export type CarouselItemType = {
    imageUrl: string;
    title: string;
    content?: string;
    dark?: boolean;
    containerStyle?: CSSProperties;
};
export type CarouselPropsType = {
    data: CarouselItemType[];
    interval?: number;
    animationInterval?: number;
    initIndex?: number;
};

/**
 * 自动轮播组件，支持不限数量的图片
 * @param {Object[]} props.data - 要进行轮播的图片数组
 * @param {String} props.data[].src - 图片的地址，用作img标签的src属性
 * @param {Number} [props.interval = 1000] - 轮播的间隔时间，单位ms
 * @param {Number} [props.initIndex = 0] - 起始索引
 * @param {Number} [props.animationInterval = 100] - 动画切换时长，单位ms
 */
const Carousel = (props: CarouselPropsType) => {
    const containerRef = useRef<HTMLDivElement>(null);
    const {
        data,
        interval = 3000,
        initIndex = 0,
        animationInterval = 500,
    } = props;
    // 每隔1s，轮播的计时器
    const timer = useRef<NodeJS.Timer>();
    // 记录动画结束后的index
    const [currentIndex, setCurrentIndex] = useState(initIndex);

    // 通过自身位置+动画 定义的style
    const containerStyle = useMemo(
        () => ({
            transitionDuration: `${animationInterval}ms`,
            transform: `translateX(${
                -currentIndex * (containerRef.current?.offsetWidth || 0)
            }px)`,
        }),
        [currentIndex, animationInterval]
    );
    const getImageContainerStyle = useCallback(
        (el: CarouselItemType, position: number) => ({
            left: (containerRef.current?.offsetWidth || 0) * position,
            backgroundImage: `url(${el.imageUrl})`,
        }),
        []
    );
    const getIndicatorActiveStyle = useCallback(
        (position: number) => {
            const active = position === currentIndex;
            return {
                visibility: (active ? "visible" : "hidden") as "visible" | "hidden",
                transitionDuration: `${interval}ms`,
                transform: `scaleX(${active ? "1" : "0"})`,
            };
        },
        [currentIndex, interval]
    );

    // 每隔${interval}ms，自动轮播下一张图，触发transition过渡动画
    useEffect(() => {
        if (data.length <= 1) return;
        timer.current = setInterval(() => {
            setCurrentIndex((prev) => (prev + 1) % data.length);
        }, interval);
        return () => {
            clearInterval(timer.current);
        };
    }, [interval, data.length]);

    return (
        <div className="containerWrapper">
            <div
                ref={containerRef}
                data-testid="container"
                className="container"
                style={containerStyle}
            >
                {data.map((el, index) => {
                    const textStyle = el.dark ? { color: "#ffffff" } : undefined;
                    return (
                        <div
                            key={`carousel-${index}`}
                            className="item"
                            style={{
                                ...getImageContainerStyle(el, index),
                                ...el.containerStyle,
                            }}
                        >
                            <div className="itemTitle" style={textStyle}>
                                {el.title}
                            </div>
                            {el.content ? (
                                <div className="itemContent" style={textStyle}>
                                    {el.content}
                                </div>
                            ) : null}
                        </div>
                    );
                })}
            </div>
            <div className="indicatorContainer">
                {data.map((_, index) => (
                    <div key={`indicator-${index}`} className="indicator">
                        <div
                            className="indicatorActive"
                            data-testid={`indicator-${index}`}
                            style={getIndicatorActiveStyle(index)}
                        />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default memo(Carousel);
