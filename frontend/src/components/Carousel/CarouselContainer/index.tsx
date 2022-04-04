/*
 * @Author: 钟媛
 * @Date: 2022-04-04 10:58:31
 * @LastEditTime: 2022-04-04 12:39:44
 * @Description: 组件 颗粒
 */
import React, { FC, useEffect, useState, useCallback, useMemo } from "react";
import cls from "classnames";
import "./index.scss";

interface ICarouselContainerProps {
  items: React.ReactElement[];
  config?: Record<string, any>;
}

interface IIndicatorItemProps {
  items: React.ReactElement[];
  currentIndex: number;
  duration: number;
  handleChange: (index: number) => void;
}

const defaultProps = {
  duration: 3000,
  speed: 300,
};

const PanelRender: FC<{ items: React.ReactElement[] }> = ({ items }) => {
  return (
    <>
      {items.map((item, index) => {
        return (
          <div
            className="loop-item"
            key={index}
            style={{ width: (1 / items.length) * 100 + "%" }}
          >
            {item}
          </div>
        );
      })}
    </>
  );
};

const Indicator = ({
  items,
  currentIndex,
  duration,
  handleChange,
}: IIndicatorItemProps) => {
  return (
    <div className="indicator">
      {items.map((item, index) => {
        const isActive = currentIndex === index;
        return (
          <div
            className="indicator-item"
            key={index}
            onClick={() => handleChange(index)}
          >
            <span
              className={cls("indicator-cover", { active: isActive })}
              style={{ animationDuration: `${duration}ms` }}
            />
          </div>
        );
      })}
    </div>
  );
};

let timer: NodeJS.Timeout | null = null;

function CarouselContainer({
  items,
  config: { duration, speed } = defaultProps,
}: ICarouselContainerProps) {
  const [currentIndex, setCurrentIndex] = useState(0);
  const total = items.length;

  const autoPlay = useCallback(() => {
    timer = setInterval(() => {
      setCurrentIndex((preIndex) => (preIndex + 1) % total);
    }, duration);

    return () => {
      if (timer) {
        clearInterval(timer);
      }
    };
  }, [duration, total]);

  // 入口
  useEffect(() => {
    const clear = autoPlay();
    return () => {
      clear();
    };
  }, [autoPlay]);

  // 动画样式
  const transformStyle = useMemo(() => {
    return {
      width: `${total * 100}%`,
      transitionDuration: `${speed}ms`,
      transform: `translateX(${(-currentIndex / total) * 100}%)`,
    };
  }, [total, speed, currentIndex]);

  const handleChange = (index: number) => {
    setCurrentIndex(index);
    if (timer) {
      clearInterval(timer);
    }
    autoPlay();
  };

  return (
    <div className="container-wrapper">
      <div className="loop-container" style={transformStyle}>
        <PanelRender items={items} />
      </div>
      <Indicator
        items={items}
        currentIndex={currentIndex}
        duration={duration}
        handleChange={handleChange}
      />
    </div>
  );
}

export default CarouselContainer;
