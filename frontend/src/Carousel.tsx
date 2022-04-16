import React, { ReactElement, useEffect, useState } from "react";
import "./Carousel.scss";

interface CarouselProps {
  children: ReactElement[]; // 子元素，必须多个，每一个代表一个分页
  speed?: number; // 播放速度（毫秒）
}

function Carousel(props: CarouselProps) {
  const { children, speed = 2000 } = props;
  const total = children.length; // 分页总数
  // console.log(children, total, speed);

  // 当前页码
  const [pageNo, setPageNo] = useState(0);

  useEffect(() => {
    let timer: ReturnType<typeof setTimeout>;

    function next() {
      timer = setTimeout(() => {
        setPageNo((prevPageNo) => {
          if (prevPageNo === total - 1) {
            return 0;
          } else {
            return prevPageNo + 1;
          }
        });

        next();
      }, speed);
    }

    next();

    return () => {
      clearTimeout(timer);
    };
  }, [total, speed]);

  const style = {
    marginLeft: `-${pageNo * 100}%`,
    transition: `margin-left 0.5s`,
  };

  const dotStyle = {
    // 使用 css 动画来实现进度条效果，动画时长根据 speed 设定
    animationDuration: `${speed / 1000}s`,
  };

  return (
    <div className="carousel">
      {/* 分页内容 */}
      <div className="carousel-pages" style={style}>
        {children.map((v: ReactElement, index) => (
          <div className="carousel-pages__item" key={index}>
            {v}
          </div>
        ))}
      </div>

      {/* 分页提示 */}
      <div className="carousel-dots">
        {children.map((v: ReactElement, index) => (
          <div className="carousel-dots__item" key={index}>
            <div
              className={`progress-bar ${pageNo === index ? "active" : ""}`}
              style={dotStyle}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Carousel;
