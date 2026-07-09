import React, { useEffect, useRef, useState } from "react";
import { DataSourceItem } from "./interface";
import "./index.css";
import { useInterval } from "../../hooks/timer";

interface CarousalProps {
  dataSource: DataSourceItem[];
  interval?: number;
  loop?: boolean;
  autoplay?: boolean;
}

interface Size {
    width: number;
    height: number;
}

const refreshTime = 50;

export default function Carousal({
  dataSource = [],
  interval = 3e3,
  autoplay = true
}: CarousalProps) {
  const containerRef = useRef<HTMLDivElement>(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [currentProgress, setCurrentProgress] = useState(0);
  const [containerSize, setContainerSize] = useState<Size>({width: 0, height: 0});

  const step = 100 / (interval / refreshTime);

  useInterval(() => {
    if (!autoplay) {
        return;
    }
    const newProgress = currentProgress + step;
    if (newProgress > 100) {
        setCurrentIndex((currentIndex + 1) % dataSource.length);
        setCurrentProgress(0);
    }
    else {
        setCurrentProgress(newProgress);
    }
  }, refreshTime);

  const carousalMainStyle = {
    width: containerSize.width * dataSource.length,
    height: containerSize.height,
    left: -currentIndex * containerSize.width
  };

  useEffect(() => {
    const width = containerRef?.current?.offsetWidth || 0;
    const height = containerRef?.current?.offsetHeight || 0;

    setContainerSize({width, height});
  }, []);

  return (
    <div className="carousal-container" ref={containerRef}>
      <div className="carousal-main" style={carousalMainStyle}>
        {dataSource.map(({background, title, desc, key}) => (
          <div className="carousal-item" key={key}>
            <img src={background} className="carousal-background" />
            <div className="carousal-item-content">
                {title && title.map(item => <h1 className="carousal-item-title" key={item}>{item}</h1>)}
                {desc && (
                    <div className="carousal-item-desc">
                        {desc.map(item => <div key={item}>{item}</div>)}
                    </div>
                )}
            </div>
          </div>
        ))}
      </div>

      {/* css中用 mix-blend-mode: difference 实现反色，所以这里也要设置背景图 */}
      <div className="carousal-indices" style={{backgroundImage: `url(${dataSource[currentIndex]?.background})`}}>
        {dataSource.map(({key}, index) => (
            <div 
                className="carousal-indices-item" 
                style={
                    index === currentIndex 
                    ? {background: `linear-gradient(to right, #000 ${currentProgress}%, #fff ${currentProgress}%)`} 
                    : {}
                }
                key={key}
            ></div>
        ))}
      </div>
    </div>
  );
}
