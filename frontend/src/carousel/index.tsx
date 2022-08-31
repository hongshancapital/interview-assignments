import React, {useEffect, useState} from "react";
import "./index.css";
import Banner from "./banner";
import Dot from "./dot";

interface IBanner{
  bgColor: string,
  title: string,
  fontColor: string,
  h2: string,
  imgUrl: File
}
interface ICarousel{
  banners: IBanner[];
  duration?: number;
}
const Carousel = (
  {
    banners,
    duration = 4000
  }: ICarousel
) => {
  let timer: NodeJS.Timer;
  const [currentIdx, setCurrentIdx] = useState<number>(0);
  const init = (duration: number = 4000) => {
    if (banners.length){
      let currentIdx = 1;
      timer = setInterval(() => {
        setCurrentIdx(currentIdx);
        currentIdx++;
        if (currentIdx > banners.length - 1){
          currentIdx = 0;
        }
      }, duration);
    }
  }
  
  useEffect(() => {
    init(duration);
    return () => {
      if (timer)
        clearInterval(timer);
    }
  }, []);
  
  return <div className='cmpContent'>
    <div className={`container`} style={{transform: `translate(-${currentIdx * 100}vw)`}}>
      {banners.map((_item: any, _index) =>
        <Banner
          key={_index}
          bgColor={_item.bgColor}
          fontColor={_item.fontColor}
          title={_item.title}
          h2={_item.h2}
          imgUrl={_item.imgUrl}
        />
      )}
    </div>
    <div className='dots'>
      {banners.map((_it: any, _idx) =>
        <Dot
          key={_idx}
          duration={duration}
          idx={_idx}
          currentIdx={currentIdx}
        />
      )}
    </div>
  </div>;
}

export default Carousel;
