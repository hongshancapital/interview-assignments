import React, { ReactElement, useRef, useState,useEffect } from "react";
import { ICarouselProps, IForwardRef } from "./CommonTypes";
import ImgContainer from "./ImgContainer/index copy";
import { SwitchDot } from "./SwitchDot";
import "./index.css";

export default function Carousel(props: ICarouselProps): ReactElement {

  
  const { imgSrcs, autoDuration, duration } = props;
  const [curIndex, setIndex] = useState<number>(0);
  const ImgRef = useRef<IForwardRef>();
  const timer = useRef<number>();

  //自动播放
  const autoSwitch = (i:number) => {
    timer.current = window.setTimeout(()=>{
        var cur = (i + 1) % imgSrcs.length;
        handleSwitch(cur)
    },autoDuration)
  }

  //清除定时器
  const clearInterval = () => {
      console.log('清除定时器')
      window.clearTimeout(timer.current)
  }
  //处理副作用
  useEffect(()=>{
    autoSwitch(curIndex)
    return clearInterval
  },[curIndex])

  /**
   * 处理移动函数
   * @param i 目标图片下标
   */
  const handleSwitch = (i: number) => {
    setIndex(i);
    if (ImgRef.current) {
      ImgRef.current.switchTo(i);
    }
  };

  return (
    <div
      id="carousel--wrap"
      className="carousel--wrap"
      style={{ width: props.width, height: props.height }}
      onMouseEnter={() => {
        window.clearTimeout(timer.current);
      }}
      onMouseLeave={()=>{
          autoSwitch(curIndex)
      }}
    >
      <ImgContainer
        ref={ImgRef}
        imgSrcs={props.imgSrcs}
        imgWidth={props.width}
        imgHeight={props.height}
        duration={props.duration}
      />
      <SwitchDot
        duration={duration}
        imgSrcs={imgSrcs}
        curIndex={curIndex}
        onChange={handleSwitch}
      />
    </div>
  );
}
