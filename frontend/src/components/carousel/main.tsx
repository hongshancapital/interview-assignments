import React, { useState, useEffect } from 'react';

import './carousel.scss'
import airpods from "../../assets/airpods.png";
import iphone from "../../assets/iphone.png";
import tablet from "../../assets/tablet.png";
const images = [
  airpods,
  iphone,
  tablet
]
const color = [  //因图片大小问题,宽高100%时图标位置不同,只能取背景颜色
  { title: 'airpods', fontColor: '#111' },
  { title: 'iphone', fontColor: '#fff' },
  { title: 'tablet', fontColor: '#111' }
]
const titles = [
  { title: 'airpods', description: airpods },
  { title: 'iphone', description: iphone },
  { title: 'tablet', description: tablet }
]

interface Props {
  timerData:number,//轮播时间间隔
  PhotosSrcData:string[]|null,//图片地址数组,因未获取后台数据,传null用本地
}
const Carousel:React.FC<Props> = (props) => {
  const {timerData,PhotosSrcData} = props
  const [status, setStatus] = useState(false);//用于连点同一个,currentIndex不变时重置定时器
  const [currentIndex, setCurrentIndex] = useState(0);//记录第几个图
  const [progressWidth, setProgressWidth] = useState(0);//进度条%

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % (PhotosSrcData||images).length);
    }, timerData);
    return () => clearInterval(timer);
  }, [currentIndex,status]);

  useEffect(() => {
    setProgressWidth(0);
    const timer = setInterval(() => {
      setProgressWidth((prevWidth) => prevWidth + 1);
    }, timerData/100);
    return () => clearInterval(timer);
  }, [currentIndex]);

  function handleClick(index: number) {
    setCurrentIndex(index);
    setProgressWidth(0)
    setStatus(!status)
  }

  return <div className='App'>
    {
      (PhotosSrcData||images).map((image, i) => (
        <div key={i} className={`carousel ${color[i].title}`} style={{ display: i === currentIndex ? 'block' : 'none', color: `${color[i].fontColor}` }}>
          <div className='carousel_details'>
            <div>
              <h4>{titles[i].title}</h4>
              <span>{titles[i].description}</span>
            </div>
            <img
              key={i}
              src={image}
              alt={`Image ${i}`}
              style={{ display: i === currentIndex ? 'block' : 'none' }}
            />
          </div>
        </div>
      ))
    }
    <div className="progress">
      {(PhotosSrcData||images).map((_, index) => (
        <div key={index} className="progress_liBox"
          style={{ width: `${100 / (PhotosSrcData||images).length - 3}%` }}
          onClick={() => handleClick(index)}
        >
          <li
            className={`progress__li ${currentIndex === index ? "active" : ""
              }`}
            style={{
              width:
                currentIndex === index
                  ? `${progressWidth}%`
                  : "0%",
            }}
          ></li>
        </div>
      ))}
    </div>
  </div>;
}

export default Carousel;
