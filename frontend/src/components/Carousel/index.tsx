import React, { useState, useEffect } from "react";
import "./index.css";

interface SwiperItem {
  id: string;
  titleList: string[];
  descList: string[];
  imageSource: string;
  mode: string;
}

interface Props {
  swiperList: SwiperItem[];
  interval: number;
}
let timer: any;
const Carousel = (props: Props) => {
  const { swiperList, interval } = props;
  const [curIndex, setcurIndex] = useState<any>(null);

  // 轮播控制器
  const actionChange = (index: number) => {
    if (index === curIndex) return;
    timer && clearInterval(timer);
    setcurIndex(index);
    initSwiper();
  };

  // 轮播初始化
  const initSwiper = () => {
    timer && clearInterval(timer);
    timer = setInterval(() => {
      setcurIndex((preVal: any = 0) => {
        let temp = preVal + 1;
        return temp < swiperList.length ? temp : 0;
      });
    }, interval);
  };

  useEffect(() => {
    setcurIndex(0);
    initSwiper();
    return () => {
      timer && clearInterval(timer);
    };
  }, []);
  return (
    <div>
      <div className="swiper-container">
        {/* 内容区域 */}
        <div
          className="swiper-wrapper"
          style={{
            transform: curIndex ? `translate(-${100 * curIndex}vw, 0)` : "none",
          }}
        >
          {swiperList.map((item: SwiperItem, index: number) => (
            <div key={item.id}>
              <div
                className="swiper-item"
                key={index}
                style={{ backgroundImage: `url(${item.imageSource})` }}
              >
                <div className="swiper-text" style={{ color: item.mode }}>
                  <div className="title">
                    {item.titleList.map((titleItem: string, index: number) => (
                      <div key={index}>{titleItem}</div>
                    ))}
                  </div>
                  <div className="text">
                    {item.descList.map((descItem: string, index: number) => (
                      <div key={index}>{descItem}</div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
        {/* 控制器区域 */}
        <div className="swiper-action-container ">
          {swiperList.map((_, index: number) => {
            return (
              <div
                className="swiper-action-item"
                key={index}
                onClick={() => {
                  actionChange(index);
                }}
              >
                <div
                  className="swiper-action-process"
                  style={{
                    width: curIndex === index ? "50px" : 0,
                    transition:
                      curIndex === index
                        ? `all linear ${interval / 1000}s`
                        : "none",
                  }}
                ></div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};
export default Carousel;
