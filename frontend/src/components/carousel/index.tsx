import React, { useEffect, useRef, useState } from "react";
import usePage from "./fn";
import "./index.scss";
interface carouselConfigOption {
  showPagination?: boolean;
  time?: number;
  children?: any[];
}
const Carousel = (props: any) => {
  const [index, setIndex] = useState(1);
  const cur = useRef<boolean>(false);
  const pageIndex = usePage(index);
  const {
    showPagination = false,
    time = 5,
    children = [],
  }: carouselConfigOption = props;
  // 跳
  const jumpPage = (index: number) => {
    pageIndex.current += index;
    setIndex(pageIndex.current);
  };
  // 指定
  const setPageIndex = (index: number) => {
    pageIndex.current = index;
    setIndex(pageIndex.current);
  };
  // 上一页
  const prevPage = () => jumpPage(-1);

  // 下一页
  const nextPage = () => jumpPage(1);

  return (
    <div className="carousel">
      <div className="carousel_item_box">
        <div
          className="carousel_item_container"
          style={{
            width: children?.length * 100 + "%",
            left: -(index - 1) * 100 + "%",
          }}
        >
          {children}
        </div>
        <div className="carousel_indicator_container">
          {Array(children.length)
            .fill("")
            .map((item, idx) => {
              return (
                <div className="carousel_indicator_item" key={"carousel" + idx}>
                  <div
                    className={`liner`}
                    style={{
                      animation: index - 1 === idx ? `${time}s move` : "",
                    }}
                    onAnimationStart={() => {
                      console.log("animation start");
                    }}
                    onAnimationEnd={() => {
                      if (index === children.length) {
                        pageIndex.current = 1;
                        setIndex(pageIndex.current)
                      } else {
                        pageIndex.current++;
                        setIndex(pageIndex.current)
                      }
                    }}
                  ></div>
                </div>
              );
            })}
        </div>
      </div>
      <div className={`${showPagination ? "visible" : ""} carousel_pagination`}>
        <button onClick={prevPage} className="arrow arrow-left">
          <svg width="18px" height="18px" viewBox="0 0 16 16">
            <g stroke="none" strokeWidth="1" fill="none" fillRule="evenodd">
              <polygon
                fill="#293040"
                fillRule="nonzero"
                points="10.7071068 12.2928932 9.29289322 13.7071068 3.58578644 8 9.29289322 2.29289322 10.7071068 3.70710678 6.41421356 8"
              ></polygon>
            </g>
          </svg>
        </button>
        {/* <span>当前页:{index}</span> */}
        <button onClick={nextPage} className="arrow arrow-right">
          <svg width="18px" height="18px" viewBox="0 0 16 16" version="1.1">
            <g stroke="none" strokeWidth="1" fill="none" fillRule="evenodd">
              <polygon
                fill="#293040"
                fillRule="nonzero"
                transform="translate(8.146447, 8.000000) scale(-1, 1) translate(-8.146447, -8.000000) "
                points="11.7071068 12.2928932 10.2928932 13.7071068 4.58578644 8 10.2928932 2.29289322 11.7071068 3.70710678 7.41421356 8"
              ></polygon>
            </g>
          </svg>
        </button>
      </div>
    </div>
  );
};
export default Carousel;
