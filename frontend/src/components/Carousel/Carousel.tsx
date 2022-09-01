import * as React from "react";
import { BaseComponent } from "./types";

export interface CarouselProps extends BaseComponent {
  /* 自动轮播，false 不自动，或传入秒数控制自动轮播速度 */
  auto?: false | number;
  /* 从第几个选项开始轮播，默认为 1 */
  startAt?: number;
  /* 是否循环播放，默认为是 */
  cycle?: boolean;
  /* 过渡动画速度，默认 300，即 300ms */
  transitionDuration?: number;
  /* 自动轮播速度，默认 3000，即 3000ms */
  speed?: number;
}

const CarouselNavigateContext = React.createContext<{
  gotoPrev: () => void;
  gotoNext: () => void;
  goto: (paper: number) => void;
}>({
  gotoPrev: () => {},
  gotoNext: () => {},
  goto: () => {},
});
export const useCarouselNavigate = () =>
  React.useContext(CarouselNavigateContext);

const Carousel: React.FC<CarouselProps> = (props) => {
  const { children, cycle, transitionDuration = 300, speed = 3000 } = props;
  const child = Array.isArray(children) ? children : [children];

  if (child.some((item) => item.type.displayName !== "Paper")) {
    throw new Error("Carousel 组件只接受 Paper 组件作为子组件");
  }

  const [wrapperWidth, setWrapperWidth] = React.useState(0);
  const contentWidth =
    wrapperWidth * (child.length === 1 ? 1 : child.length + 2);
  const [currentPaper, setCurrentPaper] = React.useState(0);
  const [towards, setTowards] = React.useState<"left" | "right">("right");

  const gotoPrev = React.useCallback(() => {
    setTowards("left");
    setCurrentPaper((prev) => prev - 1);
    if (cycle && currentPaper === 0) {
      // 过渡动画执行完毕再重置 paper
      const timer = setTimeout(() => {
        setCurrentPaper(child.length - 1);
        clearTimeout(timer);
      }, transitionDuration);
    }
  }, [cycle, currentPaper, transitionDuration]);

  const gotoNext = React.useCallback(() => {
    setTowards("right");
    setCurrentPaper((prev) => prev + 1);
    if (cycle && currentPaper === child.length - 1) {
      // 过渡动画执行完毕再重置 paper
      const timer = setTimeout(() => {
        setCurrentPaper(0);
        clearTimeout(timer);
      }, transitionDuration);
    }
  }, [cycle, currentPaper, transitionDuration]);

  const goto = React.useCallback(
    (paper: number) => {
      if (paper === currentPaper) {
        return;
      }
      if (paper > child.length - 1) {
        throw new Error("跳转目标大于轮播项总数！");
      }
      setTowards(paper - currentPaper > 0 ? "right" : "left");
      setCurrentPaper(paper);
    },
    [currentPaper]
  );

  React.useEffect(() => {
    let timer: any;
    if (cycle) {
      timer = setInterval(
        () => {
          gotoNext();
        },
        /*
         * gotoNext 中会在 transitionDuration ms 后再设置一遍 currentPaper
         * 这会让 gotoNext 更新，也就会让本 effect 更新，从而触发 clearInterval，interval 重新开始计时
         * 直接导致的结果是，每当一个循环结束，等待的时间是 speed + transitionDuration，所以此时需要减去 transitionDuration
         */
        currentPaper === 0 ? speed - transitionDuration : speed
      );
    }
    return () => {
      if (timer) {
        clearInterval(timer);
      }
    };
  }, [cycle, gotoNext, speed, transitionDuration, currentPaper]);

  return (
    <CarouselNavigateContext.Provider
      value={{
        gotoPrev,
        gotoNext,
        goto,
      }}
    >
      {currentPaper}
      <div
        className="carousel-wrapper"
        ref={(ref) => {
          setWrapperWidth(ref?.getBoundingClientRect().width ?? 0);
        }}
      >
        <div
          className="carousel-content"
          style={{
            width: `${contentWidth}px`,
            transform: `translate(${currentPaper * wrapperWidth * -1}px)`,
            transitionDuration: `${transitionDuration}ms`,
            transitionProperty: (
              towards === "right"
                ? currentPaper === 0
                : currentPaper === child.length - 1
            )
              ? "none"
              : "transform",
          }}
        >
          {child.length === 1 ? (
            children
          ) : (
            <>
              {child[child.length - 1]}
              {children}
              {child[0]}
            </>
          )}
        </div>
      </div>
    </CarouselNavigateContext.Provider>
  );
};

export default Carousel;
