import React, { useCallback, useEffect, useRef, useState } from 'react';
import Progress from './progress';
import './carousel.css';

type CarouselPropsType = {
  style?: React.CSSProperties,
  data: Array<{
    content: React.ReactNode // 内容
    key: string // 内容标识
  }>;
  intervalDuration: number; // 图片切换动画的时长
  frameDuration: number; // 每一张图片停留的时长
}

function Carousel(props: CarouselPropsType) {
  const { data, intervalDuration, frameDuration, style } = props;
  const framesRef = useRef<HTMLDivElement>(null);
  const [current, setCurrent] = useState(0);
  const [width, setWidth] = useState(document.body.clientWidth);

  useEffect(() => {
    setWidth(document.body.clientWidth);
  }, []);

  const onFrameFinish = useCallback(() => {
    // 停顿时间到了之后，执行切换帧动画
    const animation = framesRef.current?.animate({
      transform: `translateX(-${(current + 1) % data.length * width}px)`
    }, {
      duration: intervalDuration,
      fill: 'none'
    });
    // 动画执行玩之后, 刷新current为新的一帧
    animation?.addEventListener('finish', () => {
      setCurrent((current) => (current + 1) % data.length);
    });
  }, [current, intervalDuration, data]);

  return (
    <div className="carousel-container" style={style}>
      <div className="carousel-frame-list" style={{
        width: width * data.length,
        transform: `translateX(${-current * width}px)`
      }} ref={framesRef}>
        {
          data.map((item) => {
            return (
              <div className='carousel-frame' style={{ width: width }} key={item.key}>
                {item.content}
              </div>
            );
          })
        }
      </div>
      <div className="carousel-index-list">
        {
          data.map((item, index) => {
            return <Progress key={item.key} active={current === index} duration={frameDuration} onFinish={onFrameFinish} />
          })
        }
      </div>
    </div >
  )
}

export default Carousel;