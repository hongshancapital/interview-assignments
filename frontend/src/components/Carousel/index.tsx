import React, {useState, useMemo} from "react";
import { useInterval } from "./utils/useInterval";
import { CarouselProps } from "./interface";
import './styles.css'
import Indicator from "./indicator";

function Carousel(props: CarouselProps){
  const {autoPlay = true, delay = 4000, speed = 800, children} = props
  const [activeIndex, setActiveIndex] = useState(0)

  const childrenList = React.Children.toArray(children)  
  const childLength = childrenList.length

  // 计算滚动距离
  const slideDistance = useMemo(()=>{
    return activeIndex / childLength * 100
  }, [activeIndex, childLength])

  // 滚动到索引位置
  const slideTo = (index: number) => {
    setActiveIndex(index % childLength)
  }

  // 自动播放
  const resetAutoPlay = useInterval(()=>{
    slideTo(activeIndex + 1)
  }, autoPlay ? delay : null)

  // 点击指示器回调
  const clickDotCallback = (index:number) => {
    slideTo(index)
    resetAutoPlay()
  }

  return (
    <div className='carousel'>
      <div 
        className='carousel-container'
        style={{
          width: (childLength * 100) + '%', 
          transitionDuration: speed + 'ms', 
          transform: `translateX(-${slideDistance}%)`
        }}
      >
        {
          childrenList.map((slideItem, index) => 
            <div 
              className={`carousel-slide ${activeIndex === index ? 'active' : ''}`} 
              key={index}
            >
              {slideItem}
            </div>  
          )
        }
      </div>
      <Indicator 
        length={childrenList.length}
        activeIndex={activeIndex}
        delay={delay}
        onDotClick={clickDotCallback}
      />
    </div>
  );
}

export default Carousel;
