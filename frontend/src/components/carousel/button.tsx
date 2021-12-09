import React, { FC, MouseEventHandler } from "react";
import classNames from "classnames";
import './index.css';

/**
 * Button & Progress
 * progress 使用 css animation 实现
 */
const CarouselButton: FC<{
  onClick: MouseEventHandler<HTMLDivElement>,
  isActive: boolean,
  duration: number
}> = ({ onClick, isActive, duration }) => {
  return (
    <div
      className={classNames('carousel-button', { 'carousel-button-active': isActive })}
      onClick={isActive ? undefined : onClick}
    >
      {isActive ? <div className="carousel-button-progress" style={{ animationDuration: `${duration / 1000}s` }}></div> : null}
    </div>
  );
};

export default CarouselButton;