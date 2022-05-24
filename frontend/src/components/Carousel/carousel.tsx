import * as React from "react";
import classNames from "classnames";

import Pointer from "./pointer";
import Arrow from "./arrow";

import { useInterval } from "./hooks/useInterval";

import "./index.css";

export interface RefCarouselProps {
  next: () => void;
  prev: () => void;
}
export interface CarouselProps {
  children?: React.ReactNode;
  className?: string;
  showArrow?: boolean;
  autoplay?: boolean;
  duration?: number;
}
const Carousel: React.ForwardRefRenderFunction<
  RefCarouselProps,
  CarouselProps
> = (props, ref) => {
  const {
    children,
    className,
    showArrow = false,
    autoplay = false,
    duration = 3000,
  } = props;
  const count = React.Children.count(children);

  const [activeKey, setActiveKey] = React.useState<number>(-1);
  const carouselBoxRef = React.useRef<HTMLDivElement | null>(null);

  React.useEffect(() => {
    setActiveKey(0)
  }, []);

  React.useImperativeHandle(ref, () => ({
    next,
    prev,
  }));

  useInterval(
    () => {
      next();
    },
    duration,
    autoplay
  );

  const next = () => {
    const newActiveKey = activeKey === count - 1 ? 0 : activeKey + 1;
    setActiveKey(newActiveKey);
  };

  const prev = () => {
    setActiveKey(activeKey - 1);
  };

  const onClickPointer = (key: number) => {
    setActiveKey(key);
  };

  const containerClassName = classNames("carousel-container", className);

  const carouselItemStyle: React.CSSProperties = {
    transform: `translateX(-${100 * activeKey}%)`,
  };
  const carouselListStyle: React.CSSProperties = {
    width: `${100 * count}%`,
  };

  const renderCarouselItem = () => {
    return React.Children.map(
      children,
      (child: React.ReactNode, index: number) => {
        return (
          <div className="carousel-item" style={carouselItemStyle}>
            {child}
          </div>
        );
      }
    );
  };

  return (
    <div className={containerClassName}>
      <div className="carousel-box" ref={carouselBoxRef}>
        <div className="carousel-list" style={carouselListStyle}>
          {renderCarouselItem()}
        </div>
      </div>
      <Pointer
        count={count}
        activeKey={activeKey}
        onChange={onClickPointer}
        duration={duration}
      />
      <Arrow
        count={count}
        activeKey={activeKey}
        showArrow={showArrow}
        next={next}
        prev={prev}
      />
    </div>
  );
};

export default React.forwardRef(Carousel);
