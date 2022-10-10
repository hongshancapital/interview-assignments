import React, { useEffect, useRef, useState } from "react";
import { Progress } from "../Progress";
import "./index.css";

interface ICarouselProps {
    /**
     * 子元素
     */
    children: React.ReactNode;
    /**
     * 轮播时间
     */
    duration?: number;
    /**
     * 是否显示进度条
     */
    dots?: boolean;
}

/**
 * 默认配置
 */
const CarouselConfig = {
    duration: 2000
}

const Carousel = (props: ICarouselProps) => {
    /**
     * 轮播图Ref 主要用来查找父节点的 width
     */
    const carouselRef = useRef<HTMLDivElement>(null);
    /**
     * 轮播图 width
     */
    const [carouselWidth, setCarouselWidth] = useState<number>(0);
    /**
     * 当前渲染的序号
     */
    const [active, setActive] = useState<number>(0);
    /**
     * done
     * 为了得到元素宽度之后再渲染，这样打开之后可以平滑进入页面
     * todo: 如果使用 carouselWidth 去替换 done，在单元测试中无法拿到真实dom宽度，导致测试中无法渲染，需要启动浏览器的测试方法来测
     */
    const [done, setDone] = useState<boolean>(false);
    /**
     * 是否显示进度条
     */
    const dots = props?.dots === false ? false : true;

    // 获取子组件的个数
    const childrenCount = React.Children.count(props.children);

    // 轮播时间
    const duration = props.duration || CarouselConfig.duration;

    /**
     * 重新组装新的组件进行渲染
     * 给新组件加上className 和 设置的width
     */
    const carouselComponents = React.Children.map(
        props.children,
        (child, index) => {
            // 判断是否为 react 节点
            if (!React.isValidElement(child)) {
                return child;
            }
            return React.cloneElement(child, {
                className: `${child?.props?.className || ""} carousel-item`,
                key: index,
                style: {
                    width: carouselWidth,
                    ...(child.props.style || {}),
                },
            });
        }
    );

    useEffect(() => {
        setCarouselWidth(carouselRef?.current?.clientWidth || 0);
        setDone(true);
        const timer = setInterval(() => {
            if (active === childrenCount - 1) {
                setActive(0);
                return;
            }
            setActive(active + 1);
        }, duration);
        return () => {
            clearInterval(timer);
        };
    }, [active, childrenCount, duration]);

    return (
        <div className="carousel" ref={carouselRef}>
            {done && [
                <div
                    className={`carousel-container`}
                    key={`container`}
                    style={{
                        width: carouselWidth * childrenCount,
                        left: `-${active * carouselWidth}px`,
                    }}
                >
                    {carouselComponents?.map((carouselItem) => {
                        return carouselItem;
                    })}
                </div>,
                <div className={`carousel-footer`} key={`footer`}>
                    <div>
                        {dots && carouselComponents?.map((item, index) => {
                            return (
                                <span key={index}>
                                <Progress
                                    key={index}
                                    active={index === active}
                                />
                                {(carouselComponents.length !== index + 1) && <span className={`carousel-margin`} /> }
                                </span>
                            );
                        })}
                    </div>
                </div>,
            ]}
        </div>
    );
};

export { Carousel };
