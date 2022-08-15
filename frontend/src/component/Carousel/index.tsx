import React, { useState, useEffect, useRef } from "react";
import Indicator from "../Indicator";
import './index.css';

const prefix = 'carousel'
interface IProps {
  children: React.ReactNode
  duartion?: number        // 动画间隔
  animationTime?: number   //动画时间
}
const Carousel = (props: IProps) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const [CarouselItems, setCarouselItems] = useState<Array<React.ReactNode>>([])
  const containerRef = useRef<HTMLDivElement>(null);

  const { children, duartion = 3000, animationTime = 0.5 } = props;
  useEffect(() => {
    setActiveIndex(0)
    const timer = setInterval(() => {
      setActiveIndex(prevIndex => (prevIndex + 1) % (CarouselItems.length || 1))
    }, duartion)
    return () => {
      clearInterval(timer)
    }
  }, [CarouselItems, duartion])

  // 过滤掉不符合调用规则的child
  useEffect(() => {
    const validChild: JSX.Element[] = [];
    React.Children.forEach(children, child => {
      const typedChild = child as JSX.Element;
      if (typedChild?.type?.name === 'CarouselItem') {
        validChild.push(typedChild)
      }
    })
    setCarouselItems(validChild)
  }, [children])


  const containerWidth = containerRef?.current?.offsetWidth;
  const translateWidth = (containerWidth || 0) * activeIndex;

  return (
    <div
      className={`${prefix}__container`}
      ref={containerRef}
    >
      <div
        style={{
          transform: `translateX(${-translateWidth}px)`,
          display: "flex",
          transition: `all linear ${animationTime}s`
        }}
      >
        {CarouselItems}
      </div>
      <div className={`${prefix}__indicator`}>
        <Indicator
          total={CarouselItems.length}
          activeIndex={activeIndex}
          animationTime={duartion / 1000}
        />
      </div>
    </div >
  )
}
const CarouselItem: React.FC = (props) => {
  return <div className={`${Carousel}__item`}>{props?.children}</div>
}

Carousel.Item = CarouselItem;
export default Carousel;