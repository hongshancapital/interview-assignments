import React, { useState, useEffect } from "react";
interface CarouselProps {
    images: string[]; // 图片数组
    interval: number; // 轮播时间间隔
    width: number; // 宽度
    height: number; // 高度
  }

  const Carousel: React.FC<CarouselProps> = ({
    images,
    interval,
    width,
    height,
  }) => {
    const [currentIndex, setCurrentIndex] = useState(0);
    useEffect(() => {
      const timer = setInterval(() => {
        setCurrentIndex((currentIndex + 1) % images.length);
      }, interval);
      return () => clearInterval(timer);
    }, [currentIndex, images.length, interval]);
  
    return (
      <div className="div">
        <img src={images[currentIndex]}/>
      </div>
    );
  };
  
  export default Carousel; 