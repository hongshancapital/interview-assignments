import React, {useState, useEffect} from "react";
import './Carousel.scss';


interface CarouselItemProps {
    children: React.ReactNode
}
/**
 * @param {children} children ReactNode
 * @returns 轮播图 单项
 */
export const CarouselItem = (props: CarouselItemProps = {children: React.createElement("div")}) => {
    return (
        <div
            className={'carousel_item'}
            style={{width: '100%', height: '100%'}}
        >
            {props.children}
        </div>
    );
};


interface CarouselInfoProps {
    image?: string;
}
/**
 * @param {image} image 图片
 * @returns 轮播图 主体
 */
export const CarouselInfo = (props: CarouselInfoProps) => {
    return (
        <div className="carousel_info_container">
            <div className="carousel_info_image_container">
                <img src={props.image} alt="Jay" className="carousel_info_image"/>
            </div>
        </div>
    );
};

/**
 * @param {children} children ReactNode
 * @param {switchingTime} switchingTime
 * @returns 轮播图 容器
 */
const Carousel = ({
                      children = React.createElement("div"),
                      switchingTime = 5000,// 间隔时间 默认5秒 以毫秒为单位
                  }) => {
    const time = ((switchingTime % 60000) / 1000).toFixed(0); // 将毫秒转换为秒
    const [activeIndex, setActiveIndex] = useState(0); // 对应索引

    /**
     * 更新索引
     * @param {newIndex} newIndex 更新索引
     */
    const onUpdateIndex = (newIndex: number) => {
        if (newIndex < 0) {
            newIndex = React.Children.count(children) - 1;
        } else if (newIndex >= React.Children.count(children)) {
            newIndex = 0;
        }
        setActiveIndex(newIndex);
        replayAnimations();
    };

    /**
     * 重置动画
     */
    const replayAnimations = () => {
        document.getAnimations().forEach((anim) => {
            anim.cancel();
            anim.play();
        });
    };

    /**
     * 底部加载条点击事件
     * @param {index} index 跳转索引
     */
    const onClickCarouselIndex = (index: number) => {
        onUpdateIndex(index);
        replayAnimations();
    };

    useEffect(() => {
        const interval = setInterval(() => {
            onUpdateIndex(activeIndex + 1);
        }, switchingTime);

        return () => {
            if (interval) {
                clearInterval(interval);
            }
        };
    });

    return (
        <div className={'container'}>
            <div
                className={'inner'}
                style={{transform: `translateX(-${activeIndex * 100}%)`}}
            >
                {React.Children.map(children, (child) => {
                    // @ts-ignore
                    return React.cloneElement(child, {width: "100px", height: "100%"});
                })}
            </div>
            <div className={'loading'}>
                {React.Children.map(children, (child, index) => {
                    return (
                        <div
                            className={'indicator_outer'}
                            onClick={() => onClickCarouselIndex(index)}
                        >
                            <div
                                className={'indicator_inside'}
                                style={{
                                    backgroundColor: index === activeIndex ? "#FFFFFF" : '',
                                    animationDuration: index === activeIndex ? `${time}s` : "0s",
                                }}></div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default Carousel;

