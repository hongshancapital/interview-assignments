import { ReactNode, useState, useEffect } from 'react';

import './Carousel.css';

interface ICarouselProps {
  autoplay?: boolean; // 自动播放
  interval?: number; // 切换间隔 ms
  children: ReactNode[];
}

function Carousel(props: ICarouselProps) {
  const { autoplay = true, interval = 3000, children } = props;
  const [current, setCurrent] = useState(-1);

  useEffect(() => {
    if (!autoplay) return;

    setCurrent(0); // 触发底部切换按钮动画
    let timer = setInterval(() => {
      setCurrent((oldVal) => {
        return oldVal === children.length - 1 ? 0 : oldVal + 1;
      });
    }, interval);

    return () => {
      clearInterval(timer);
    };
  }, []);

  return (
    <div className="carousel-container">
      <div
        className="carousel-wrap"
        style={{
          transform: `translate(-${100 * current}%, 0)`,
          transition: `transform 1s ease`,
        }}
      >
        {props.children}
      </div>
      <div className="dots-wrap">
        {props.children.map((item, index) => {
          return (
            <div className="dots-item" key={index} onClick={() => setCurrent(index)}>
              <div
                style={{ transitionDuration: `${autoplay ? interval / 1000 : 0}s` }}
                className={index === current ? 'active progress' : 'progress'}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Carousel;
