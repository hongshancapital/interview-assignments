import React, {useEffect, useRef , useState} from "react";
import { CarouselProps } from './carouseProps';
import { ImageInfo } from "../../constants/ImageInfo";
import './index.scss'

const Carousel: React.FC< CarouselProps > = ({ list, time = 3 })=>{
    const [currentIndex, setCurrentIndex] = useState(0);
    const [transformloading, setTransformloading] = useState(false);
    const carouselRef = useRef<HTMLDivElement>(null);


    useEffect(() => {
      const nextIndex = (currentIndex + 1) % list.length;
      const example = setTimeout(() => {
        change(nextIndex);
      }, time*1000);
  
      return () => {
        clearTimeout(example);
      };
    }, [currentIndex, list.length, time]);
  
  
    const change = (index: number) => {
      setTransformloading(true);
      setCurrentIndex(index);
    };

    return (
      <div className="carousel-wrapper">
        <div
          className="carousel-content"
          ref={carouselRef}
          style={{ transform: `translateX(-${currentIndex * 100}vw)`, transition: transformloading ? 'transform 0.5s' : 'none' }}
        >
          {list.map((item:ImageInfo, index:number) => (
            <div className="carousel-item" 
              key={item.key}
              list-label="carousel-item"
              style={{color:item.color}}  
            >
              <div className='carousel-item-title'
                dangerouslySetInnerHTML={{ __html: item.title }}/>
              <div className='carousel-item-subTitle'
                dangerouslySetInnerHTML={{ __html: item.subTitle }}/>
                
              <img src={item.src} alt={item.title} onClick={() => change(index)} />
            </div>
          ))}
        </div>
        <div className="carousel-step">
          {list.map((item:ImageInfo, index:number) => (
            <div className="carousel-step-item" key={item.key}>
              <div className='carousel-step-actived' 
                style={{width:currentIndex===index?'100%':0,transition:currentIndex===index?`${time}s ease-in-out`: 'none'}}
               />
            </div>
          ))}
        </div>
      </div>
    );

}

export default Carousel;