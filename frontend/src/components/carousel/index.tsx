import React, {
  ReactElement,
  ReactNode,
  useRef,
  useEffect,
  useState,
} from "react";
import classNames from "classnames";
import "./index.css";
import useInterval from "../../hooks/useInterval";
import useHover from "../../hooks/useHover";

interface ICarouselProps {
  duration?: number;
  hoverPause?: boolean;
  className?: string;
  children?: ReactNode;
}

/**
 * @description: 轮播图组件，应当有一组平级的子元素
 * @param {number} duration 自动轮播时间
 * @param {boolean} hoverPause 是否启用hover暂停功能
 * @param {string} className 自定义外层div的className
 */
function Carousel({
  duration = 3000,
  children,
  hoverPause = true,
  className,
}: ICarouselProps): ReactElement {
  const [current, setCurrent] = useState(0);
  const carouselRef = useRef<HTMLDivElement>(null);
  const count = React.Children.count(children);
  const prevCount = useRef(count);

  //检测hover状态
  const pause = useHover(carouselRef.current) && hoverPause;

  // 如果子元素数量减少，重置当前指向的元素并刷新计时器
  useEffect(() => {
    if (count < current + 1) {
      setCurrent(0);
      prevCount.current = count;
      reset();
    }
  }, [count, current]);

  const reset = useInterval(
    () => {
      setCurrent((current) => (current < count - 1 ? current + 1 : 0));
    },
    duration,
    { pause }
  );

  return (
    <div className={classNames("carousel", className)} ref={carouselRef}>
      {count && (
        <div
          className="list"
          style={{ transform: `translateX(-${current * 100}%)` }}
        >
          {React.Children.map(children, (child) => {
            return <div className="item">{child}</div>;
          })}
        </div>
      )}

      <div className="dots">
        {new Array(count).fill("").map((_, index) => (
          <div
            key={index}
            className="dot"
            onClick={() => {
              if (index !== current) {
                reset();
                setCurrent(index);
              }
            }}
          >
            {index === current && (
              <div
                className="dotLoading"
                style={{
                  animationDuration: duration / 1000 + "s",
                  animationPlayState: pause ? "paused" : "running",
                }}
              ></div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Carousel;
