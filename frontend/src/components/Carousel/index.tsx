import React, { useState, useEffect } from "react";
import "./index.css";
import pic1 from "./pic1.jpeg";
import pic2 from "./pic2.jpeg";
import pic3 from "./pic3.jpeg";
import Indicator from './components/Indicator'
export interface ICarouselProps {
  // loop?: boolean,
  // delay?:number,
  // autoPlay?: boolean,
  // imageList?:string[]
}
export const Carousel = (props: ICarouselProps) => {
  // const {
  //   loop,
  //   delay,
  //   autoPlay,
  //   imageList
  // }=props
  const [currentIndex, setCurrentIndex] = useState(0);
  const list = [pic1, pic2, pic3];
  const itemLength = list.length;

  useEffect(() => {
    const timer = setTimeout(() => {
      setCurrentIndex(currentIndex + 1 === itemLength ? 0 : currentIndex + 1);
    }, 3000);
    return () => clearTimeout(timer);
  }, [currentIndex,itemLength]);

  return (
    <div className="carousel-wrapper">
      <Indicator length={3} delay={3000} index={0}></Indicator>
      <div
        className="carousel-content"
        style={{
          transform: `translateX(${currentIndex * 100 * -1}%)`,
        }}
      >
        {list.map((url, index) => (
          <div key={url + index} className="img-wrapper">
            <img className="image" alt="img" src={url} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
