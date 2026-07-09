import './index.css';
import { ReactNode, useState, useRef, useEffect } from 'react';

type Props = {
  children: ReactNode
}

type CarouselProps = {
  children: Array<ReactNode>
  width?: string,
  height?: string
}

type DotsProps = {
  dotsNum: number,
  current: number
}

// 每个轮播图内容
function CarouselItem(props: Props) {
  return (
    <div className="carousel-item" >
      { props.children }
    </div>
  );
}

// 轮播图进度条
function CarouselDots(props: DotsProps) {
  let dots: Array<null> = Array(props.dotsNum).fill(null);
  return (
    <div className="carousel-dots">
      {
        dots.map((dot, index) => {
          return (
            <div className="carousel-dot" key={ 'dot-' + index }>
              <div className={ 'carousel-dot-inner ' + (index === props.current ? 'carousel-inner-active' : '')} ></div>
            </div>
          );
        }) 
      }
    </div>
  );
}

function Carousel(props: CarouselProps) {
  let timer: any;
  let timeouter: any;
  let containerRef = useRef<HTMLDivElement | null>(null);
  let [translateX, setTranslateX] = useState<number>(0);
  let [duration, setDuration] = useState<number>(0.2);
  let [loopIndex, setLoopIndex] = useState<number>(-1);
  let carouselList: Array<ReactNode> = props.children.slice();
  if (carouselList.length > 1) {
    // 解决轮播图最后一张图片出现反向滚动问题
    carouselList = carouselList.concat(carouselList[0]);
  }

  // 开启滚动定时器
  function startTimer(width: number) {
      let fn = () => {
        let currentIndex = loopIndex + 1;
        setDuration(0.2);
        setLoopIndex(currentIndex);
        setTranslateX(-(currentIndex * width));
        if (currentIndex === carouselList.length -1) {
          // 处理最后一张图片问题
          timeouter = setTimeout(() => {
            setDuration(0);
            setLoopIndex(0);
            setTranslateX(0);
            clearTimeout(timeouter);
            timeouter = null;
          }, 300);  
        }
      };
      // 解决首次图标进度条不动问题
      if (loopIndex === -1) {
        setLoopIndex(0);
      }
      timer = setInterval(fn, 3000);
  }

  // 清除定时器
  function clearTimer() {
    clearInterval(timer);
    timer = null;
  }
  
  useEffect(() => {
    let width: number = (containerRef && containerRef.current &&
      containerRef.current.clientWidth) || 0;
    startTimer(width);
    return clearTimer;
  }, [loopIndex])

  return (
    <div ref={ containerRef } className='carousel-container'
        style={{ width: props.width, height: props.height }}
    >
      <div className="carousel-items-wraper" style={{ 
          transform: "translate(" + translateX + "px, 0px)",
          transition: "transform " + duration + "s linear 0s" 
      }}>
        {
          carouselList.map((item: ReactNode, index: number) => {
            return (
              <CarouselItem children={ item } key={ 'item_' + index } />
            )
          }) 
        }
      </div>
      <CarouselDots dotsNum={ props.children.length } current={ loopIndex }></CarouselDots>
    </div>
  );
}

export default Carousel;
