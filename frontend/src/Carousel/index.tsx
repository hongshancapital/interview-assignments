import "./index.css";
import React from "react";
import SlickCarousel from "@ant-design/react-slick";

/**
 * 如果追求简洁，那么按照视频要求写死参数即可，如果追求组件完整性以及扩展性，包括后期的一些接口暴露的问题，以及动态滚动组件内容更新的问题
 */

const Carousel = (props: any) => {
    const slickRef = React.useRef<any>();
    // 封装react-slick goto方法
    const goTo = (slide: number, dontAnimate = false) => {
        slickRef.current.slickGoTo(slide, dontAnimate);
    };
    //   获取当前node节点
    const prevCount = React.useRef(React.Children.count(props.children));
    //   监听Carousel内部元素是否发生变化，如果变化从第一个开始滚
    React.useEffect(() => {
        if (prevCount.current !== React.Children.count(props.children)) {
            goTo(0, false);
            prevCount.current = React.Children.count(props.children);
        }
    }, [props.children]);
    // 参数设置，这里的很多的参数可以通过props注入，为了简洁起见就全部写死了。后期根据需要开放即可。
    const settings = {
        dots: true,
        dotsClass: "slick-dots",
        infinite: true,
        autoplay: true,
        autoplaySpeed: 3000,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        ...props,
    };
    return <SlickCarousel ref={slickRef} {...settings}></SlickCarousel>;
};

export default Carousel;
