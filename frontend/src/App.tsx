// 导入React模块和useState，useEffect，useRef函数
import React, { useState, useEffect, useRef } from "react";
import './App.css';

// 声明一个CarouselItem接口，表示每个项目的数据结构
interface CarouselItem {
  id: number; // 项目的唯一标识
  image: string; // 项目的图片链接
  title: string; // 项目的标题
  description: string; // 项目的描述
}

// 声明一个Carousel组件，接受一个items数组作为属性，表示要显示的项目列表
const Carousel = ({ items }: { items: CarouselItem[] }) => {
  // 使用useState来声明一个current变量，表示当前显示的项目的索引，初始值为0
  const [current, setCurrent] = useState(0);

  // 使用useRef来声明一个timer变量，表示自动播放的定时器
  const timer = useRef<NodeJS.Timeout | null>(null);

  // 使用useEffect来设置和清除定时器，实现自动播放的逻辑
  useEffect(() => {
    // 设置定时器，每隔3秒钟切换到下一个项目
    timer.current = setInterval(() => {
      setCurrent((prev) => (prev + 1) % items.length);
    }, 3000);

    // 返回一个清除函数，在组件卸载时清除定时器
    return () => {
      if (timer.current) {
        clearInterval(timer.current);
      }
    };
  }, [items.length]);

  // 定义一个handlePrev函数，用来处理点击左箭头的事件，切换到上一个项目
  const handlePrev = () => {
    setCurrent((prev) => (prev - 1 + items.length) % items.length);
  };

  // 定义一个handleNext函数，用来处理点击右箭头的事件，切换到下一个项目
  const handleNext = () => {
    setCurrent((prev) => (prev + 1) % items.length);
  };

  // 时间滚动条
  const actionItem = items.map((i, index) => {
    return (
      <div className='actionItemStyle' key={index} onClick={() => setCurrent(index)}>
        <div className='actionLineStyle' style={{ backgroundColor: current === index ? '#fff' : '#0000001a' , animationDuration: index === current ? `3s` : undefined,
}} ></div>
      </div>
    )
  })
  // 返回一个JSX元素，表示Carousel组件的UI结构
  return (
    <div className="carousel">
      {/* 显示当前项目的图片 */}
      <img src={items[current].image} alt={items[current].title} />
      {/* 显示当前项目的标题和描述 */}
      <div className="carousel-info">
        <h3>{items[current].title}</h3>
        <p>{items[current].description}</p>
      </div>
      {/* 显示左右箭头，用来切换项目 */}
      <div className="carousel-controls">
        <button onClick={handlePrev}>&lt;</button>
        <button onClick={handleNext}>&gt;</button>
      </div>
      <div className='actionStyle'>
          {actionItem}
        </div>
    </div>
  );
};

// 导出Carousel组件
export default Carousel;