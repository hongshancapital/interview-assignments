import React, {useCallback, useEffect, useMemo, useState} from "react";
import classNames from "classnames";

import "./index.scss";

interface ICarouselProps {
  // 自动切换的时间, 单位ms
  interval?: number;
  // 过渡动画时间，单位ms
  duration?: number;
}

export default function Carousel(props: React.PropsWithChildren<ICarouselProps>) {
  const [current, setCurrent] = useState(0);

  const {children, interval = 3000, duration = 300} = props;

  const list = children ? React.Children.toArray(children) : [];

  // 自动播放
  const autoPlay = useCallback(() => {
    setCurrent((cur) => (list.length === 0 ? 0 : (cur + 1) % list.length));
  }, [list.length]);

  // 当list长度变化后，校验current是否越界
  useEffect(() => {
    if (list.length < current) {
      setCurrent(0);
    }
  }, [list.length, current]);

  // 开启定时器, 当list.length或者interval变化时重启新的循环
  useEffect(() => {
    const timer = setInterval(autoPlay, interval);
    return () => clearInterval(timer);
  }, [interval, autoPlay]);

  // 计算容器的style
  const containerStyle = useMemo(() => {
    if (list.length === 0) {
      return {
        width: "100%",
        transition: `all ${duration}ms`,
      };
    } else {
      return {
        transform: `translateX(-${(current * 100) / list.length}%)`,
        width: `${100 * list.length}%`,
        transition: `all ${duration}ms`,
      };
    }
  }, [list.length, current, duration]);

  return (
    <div className="carousel">
      <div className="container" style={containerStyle}>
        {list.map((ele, index) => (
          <div key={index}>{ele}</div>
        ))}
      </div>
      <div className="dots">
        {list.map((_, index) => (
          <div className="dot" onClick={() => setCurrent(index)}>
            <div className={index === current ? 'selected' : ''} style={{  animationDuration: `${interval}ms` }} />
          </div>
        ))}
      </div>
    </div>
  );
}
