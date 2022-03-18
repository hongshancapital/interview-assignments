import React, { useEffect, useMemo, useRef, useState } from "react";
import animationUtils from '../../utils/animation';

import { CarouselDataType } from '../../typings/component';

import './index.css';

interface CarouselProps {
  // 轮播图对象数组
  data: CarouselDataType[];
  // 轮播间隔（ms），默认3s
  delay?: number;
};

// 待入选props参数
// 闪屏时间 0.5s
const flash_time = 0.5;

const Carousel: React.FC<CarouselProps> = (props) => {
  const { data, delay = 3000 } = props;
  const [page, setPage] = useState<number>(0);
  const carouselRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const timer = setTimeout(() => {
      setPage(data.length - 1 === page? 0 : page + 1);
      clearTimeout(timer);
    }, delay);
  }, [page]);

  const renderContent = () => {
    return data.map((current: CarouselDataType, index: number) => {
      return (
        <div className="carousel-content-box" style={{ backgroundImage: `url(${current.backgroundImg})`}} key={`${current.id}-${index}`}>
          <div className="carousel-text-box">
            {current.title.map((t: string, index: number) => <div className="title" key={`${t}-${index}`} style={{ color: current.color }}>{t}</div>)}
            {current.description?.map((d: string, index: number) => <div className="text" key={`${d}-${index}`} style={{ color: current.color }}>{d}</div>)}
          </div>
        </div>
      );
    });
  };

  const renderPagination = () => {
    return (
      <div className="pagination-box">
        {data.map((_current: CarouselDataType, index: number) => {
          return (
            <div className="pagination" key={`pagination-${index}`}>
              {page === index && <div className="pagination-fill" style={{ animationDuration: `${delay / 1000}s` }}></div>}
            </div>
          );
        })}
      </div>
    );
  };

  // 切换闪屏动画
  useMemo(() => {
    const ref = carouselRef.current;
    if (ref) {
      if (page > 0) {
        animationUtils.scrollXAnimation(ref, document.body.clientWidth * page, flash_time);
      } else {
        animationUtils.scrollXAnimation(ref, 0, flash_time);
      }
    }
  }, [page]);

  return (
    <div className="carousel-box" ref={carouselRef}>
      {renderContent()}
      {renderPagination()}
    </div>
  );
};

export default Carousel;