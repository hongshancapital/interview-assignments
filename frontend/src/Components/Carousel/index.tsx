import React, { useEffect, useRef, useState } from "react";
import CasrouselItem from "./CarouselItem";
import { CasrouselProps } from "./Casrousel.types";
import CarouselBar from "./CarouselBar";
import "./styles/CasrouselItem.css";
let TIMER: NodeJS.Timer;

const Casrousel: React.FC<CasrouselProps> = (props) => {
  const { casrouselList, activeIndex = 0, isAuto = true, wait = 3000 } = props;
  const caLen = casrouselList.length;
  const currentIndex = useRef(activeIndex);
  // 帮助useRef进行页面数据的更新
  const [, setFresh] = useState(activeIndex);
  // 设置自动滚动
  const [autoConfig, setAutoConfig] = useState(isAuto);

  // 自动播放功能
  const autoDisplay = () => {
    if (isAuto) {
      TIMER = setInterval(() => {
        if (currentIndex.current < caLen - 1) {
          currentIndex.current = currentIndex.current + 1;
        } else {
          currentIndex.current = 0;
        }
        setFresh(currentIndex.current);
      }, wait);
    }
  };
  // 判断当前鼠标移入，如果移入，那么滚动停止
  const mouseEnter = () => {
    setAutoConfig(false);
    clearInterval(TIMER);
    // autoDisplay()
  };
  // 当鼠标移出，则继续动画
  const mouseLeave = () => {
    if(isAuto){
      setAutoConfig(true);
    }
    autoDisplay();
  };
  // 手动切换
  const selectIndex = (index: number) => {
    if (index === currentIndex.current) return;
    if(TIMER) clearInterval(TIMER)
    currentIndex.current = index;
    setFresh(currentIndex.current);
  };
  useEffect(() => {
    if (TIMER) clearInterval(TIMER);
    autoDisplay();
    return () => {
      clearInterval(TIMER);
    };
  }, []);
  return (
    <div
      className="casrousel-lists"
      onMouseEnter={mouseEnter}
      onMouseLeave={mouseLeave}
    >
      <div
        className="casrousel-slides"
        style={{
          transform: `translateX(-${currentIndex.current * 100}%)`,
        }}
      >
        {casrouselList.map((item) => (
          <CasrouselItem casrouseInfo={item} key={item.id} />
        ))}
      </div>
      <CarouselBar
        autoConfig={autoConfig}
        barLen={caLen}
        activeIndex={currentIndex.current}
        wait={wait}
        onMouseEnter={mouseEnter}
        selectIndex={selectIndex}
      />
    </div>
  );
};

export default Casrousel;
