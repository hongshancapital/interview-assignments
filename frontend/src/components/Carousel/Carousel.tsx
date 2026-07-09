// <!---------------------------
// Name: Carousel
// File: carousel
// -----------------------------
// Author: YuCheng
// Telphone: 13212707252
// Email: yu656@qq.com
// Data:   1/10/2023, 4:51:59 PM
// ---------------------------->
import React, { useEffect, useRef, useState } from "react";
import "./Carousel.css";

interface props {
  speed?: number;
}
const Carousel = (props: props) => {
  const wrapperRef = useRef<any>();
  const [currentPage, setCurrentPage] = useState<number>(1); //当前显示页
  const [translateX, setTranslateX] = useState<number>(0); //偏移量
  const [viewWidth, setViewWidth] = useState<number>(0); //窗口宽度
  const [childNodes, setChildNodes] = useState<Array<number>>([]); //页码数组
  const [timer, setTimer] = useState<NodeJS.Timeout>(); //定时器
  const [hovered, setHovered] = useState<boolean>(false); //鼠标移入移出状态

  useEffect(() => {
    handleResize();
  }, [wrapperRef]);

  // 定时切换，设置当前页
  useEffect(() => {
    //鼠标移入状态时不启用定时器
    if (!hovered) {
      const timer = setTimeout(() => {
        currentPage === 3 || currentPage < 1
          ? setCurrentPage(1)
          : setCurrentPage(currentPage + 1);
      }, props.speed || 5000);
      setTimer(timer);
    }
  }, [currentPage]);

  // 根据当前页设置偏移量
  useEffect(() => {
    currentPage <= 1
      ? setTranslateX(0)
      : setTranslateX(-(viewWidth * (currentPage - 1)));
  }, [currentPage]);

  useEffect(() => {
    // 监听窗口变化
    window.addEventListener("resize", handleResize);
    // 销毁监听
    return () => window.removeEventListener("resize", handleResize);
  }, [wrapperRef]);

  // 窗口大小改变重新赋值方法
  const handleResize = () => {
    let nodeArr: Array<number> = [];
    wrapperRef?.current?.firstChild?.childNodes.forEach(
      (item: any, index: number) => {
        nodeArr.push(index);
      }
    );
    setChildNodes(nodeArr);
    setViewWidth(wrapperRef?.current?.clientWidth);
  };

  //鼠标移入切换到指定页
  const mouseEnter = (index: number) => {
    setHovered(true);
    setCurrentPage(index + 1);
    clearTimeout(timer);
  };

  //鼠标移出重置为定时切换
  const mouseLeave = (index: number) => {
    setHovered(false);
    setCurrentPage(1);
  };

  //移动端左右滑动切换--滑动开始事件
  let startX: number, endX: number;
  const handelTouchStart = (e: any) => {
    startX = e.touches[0].clientX;
  };
  //移动端左右滑动切换--滑动平移事件
  const handelTouchMove = (e: any) => {
    endX = e.touches[0].clientX;
  };

  //移动端左右滑动切换--滑动结束事件
  const handelTouchend = (e: any) => {
    if (startX - endX > 40) {
      setHovered(true);
      setCurrentPage(currentPage + 1);
      clearTimeout(timer);
    } else if (endX - startX > 40) {
      setHovered(true);
      setCurrentPage(currentPage - 1);
      clearTimeout(timer);
    }
  };

  //监听左右滑动切换
  useEffect(() => {
    wrapperRef?.current?.addEventListener("touchstart", handelTouchStart,{passive: true});
    wrapperRef?.current?.addEventListener("touchmove", handelTouchMove,{passive: true});
    wrapperRef?.current?.addEventListener("touchend", handelTouchend,{passive: true});
    return () => {
      wrapperRef?.current?.removeEventListener("touchstart", handelTouchStart);
      wrapperRef?.current?.removeEventListener("touchMove", handelTouchMove);
      wrapperRef?.current?.removeEventListener("touchend", handelTouchend);
    };
  }, [currentPage]);

  return (
    <div className="wrapper" ref={wrapperRef}>
      <div
        className="imgList"
        style={{ transform: `translateX(${translateX}px)` }}
      >
        <div
          className="imgItem"
          style={{
            backgroundImage: `url(${require("../../assets/iphone.png")})`,
            backgroundColor: "#111111",
            color: "#ffffff",
          }}
        >
          <div className="imgTitle">xPhone</div>
          <div className="imgDes">
            Lots to love.Less to spend.
            <br />
            Strting at $399.
          </div>
        </div>
        <div
          className="imgItem"
          style={{
            backgroundImage: `url(${require("../../assets/tablet.png")})`,
            backgroundColor: "#fafafa",
            color: "#000000",
          }}
        >
          <div className="imgTitle">Tablet</div>
          <div className="imgDes">Just the right amount of everything.</div>
        </div>
        <div
          className="imgItem"
          style={{
            backgroundImage: `url(${require("../../assets/airpods.png")})`,
            backgroundColor: "#f1f1f3",
            color: "#000000",
          }}
        >
          <div className="imgTitle">
            Buy a Tablet or xPhone for college.
            <br /> Get airPods.
          </div>
          <div className="imgDes"></div>
        </div>
      </div>
      <div className="dotList">
        <>
          {childNodes.map((item: number, index: number) => {
            return (
              <div
                className="dotItem"
                key={index}
                onMouseEnter={() => mouseEnter(index)}
                onMouseLeave={() => mouseLeave(index)}
              >
                {currentPage - 1 === index ? (
                  <div
                    className="inner line"
                    style={{
                      animation: `progress ${props.speed || 5000}ms  1 linear`,
                    }}
                  ></div>
                ) : (
                  ""
                )}
              </div>
            );
          })}
        </>
      </div>
    </div>
  );
};
export default Carousel;
