import React, {
    forwardRef,
    useCallback,
    useState,
    useImperativeHandle,
    useRef,
    useEffect
  } from "react";
  import classNames from "classnames";
  import debounce from "./debounce";
  import useInterval from "./useInterval";
  import useStateRef from "./useStateRef";
  import "./carousel.css";
  
  type CarouselRef = {
    swipeTo: (index: number) => void;
    prev: () => void;
    next: () => void;
  };
  
  type CarouselProps = {
    autoPlay?: number;
    selectedIndex?: number;
    children?: React.ReactNode;
    className?: string;
    touchable?: boolean;
    duration?: number;
    indicator?: React.ReactNode;
    showIndicators?: boolean;
    showDots?: boolean;
    dots?: React.ReactNode;
    style?: React.CSSProperties;
    onChange?: (selected: number, prevSelected: number) => void;
  };
  
  const prefix = "eve";
  
  const Carousel = forwardRef<CarouselRef, CarouselProps>(
    (
      {
        autoPlay = 3000,
        selectedIndex = 0,
        touchable = true,
        children,
        className = "",
        duration = 300,
        showIndicators = true,
        showDots = true,
        dots = null,
        indicator = null,
        style = {},
        onChange = () => {}
      }: CarouselProps,
      ref
    ) => {
      const [node, setNode] = useState<HTMLDivElement | null>(null);
      const [carouselWidth, setCarouselWidth, carouselWidthRef] = useStateRef(0);
      const lastStarX = useRef<number>(0);
      const [active, setActive, activeRef] = useStateRef(selectedIndex);
      const [swiping, setSwiping, swipingRef] = useStateRef(false);
      const [swipeStyle, setSwipeStyle, swipeStyleRef] = useStateRef({
        transform: "translate3d(0px, 0, 0)",
        transitionDuration: "0ms",
        width: "0px"
      });
      const container = useRef<HTMLDivElement | null>(null);
      const count = React.Children.count(children);
      const childrenArr = React.Children.toArray(children);
      let swipeItems: React.ReactNode[] = [];
      // 移动元素
      const translate = (distance: number, selected: number, isSlient = true) => {
        setSwipeStyle({
          ...swipeStyle,
          ...{
            transform: `translate3d(${distance}px, 0, 0)`,
            transitionDuration: duration + "ms"
          }
        });
        setTimeout(() => {
          if (swipingRef.current) setSwiping(false);
          if (isSlient) {
            onChange(selected, activeRef.current);
            setActive(selected);
          }
        }, duration);
      };
      // 当移动到clone的swipe组件的时候，reset位置
      const resetPosition = (resetActived: number) => {
        const distance = -(resetActived + 1) * carouselWidth;
        setTimeout(() => {
          setSwipeStyle({
            ...swipeStyle,
            transform: `translate3d(${distance}px, 0, 0)`,
            transitionDuration: "0ms"
          });
        }, duration);
      };
      // 获取carousel外层的width
      const carouselContainerRef = useCallback((node) => {
        if (node !== null) {
          setNode(node);
        }
      }, []);
  
      const move = (pace: number): void | boolean => {
        const movePace = active + pace;
        let target = movePace;
        const distance = -(target + 1) * carouselWidth;
        const needRest = movePace >= count || movePace <= -1;
        const resetActived = movePace >= count ? 0 : count - 1;
        translate(distance, needRest ? resetActived : target, pace !== 0);
        if (needRest) {
          resetPosition(resetActived);
        }
      };
      // 跳转到具体的页面
      const moveTo = (index: number) => {
        const distance = -(index + 1) * carouselWidth;
        setSwiping(true);
        translate(distance, index);
      };
  
      const prev = () => {
        setSwiping(true);
        move(-1);
      };
  
      const next = () => {
        setSwiping(true);
        move(1);
      };
  
      const onStartTouch = (e: React.TouchEvent) => {
        const touch = e.targetTouches[0];
        lastStarX.current = touch.pageX;
        setSwiping(false);
      };
  
      const onMoveTouch = (e: React.TouchEvent) => {
        if (touchable || swipingRef.current) {
          const touch = e.targetTouches[0];
          const distance =
            touch.pageX -
            lastStarX.current -
            (activeRef.current + 1) * carouselWidthRef.current;
          setSwipeStyle({
            ...swipeStyleRef.current,
            ...{
              transform: `translate3d(${distance}px, 0, 0)`,
              transitionDuration: duration + "ms"
            }
          });
        }
      };
  
      const onEndTouch = (e: React.TouchEvent) => {
        const touch = e.changedTouches[0];
        const distance = touch.pageX - lastStarX.current;
        const shouldSwipe = Math.abs(distance * 3) > carouselWidthRef.current;
        if (distance < 0 && shouldSwipe) {
          move(1);
        } else if (distance > 0 && shouldSwipe) {
          move(-1);
        } else {
          move(0);
        }
      };
      // 初始化
      useEffect(() => {
        if (node) {
          const measure = () => {
            setCarouselWidth(node.getBoundingClientRect().width);
          };
          const handleResize = debounce(measure, 200);
          measure();
          window.addEventListener("resize", handleResize);
          return () => {
            window.removeEventListener("resize", handleResize);
            handleResize.cancle();
          };
        }
      }, [node]);
      // 定时器
      useInterval(
        () => {
          move(1);
        },
        autoPlay > 0 && !swiping ? autoPlay : null
      );
      // 对外暴露的组件实例
      useImperativeHandle(ref, () => ({
        swipeTo: (index: number) => {
          const target = index >= count ? count - 1 : index;
          moveTo(target);
        },
        prev: () => {
          prev();
        },
        next: () => {
          next();
        }
      }));
  
      useEffect(() => {
        setSwipeStyle({
          ...swipeStyle,
          ...{
            transform: `translate3d(${-(active + 1) * carouselWidth}px, 0, 0)`,
            transitionDuration: duration + "ms"
          }
        });
      }, [carouselWidth]);
  
      // touch监听
      useEffect(() => {
        if (container.current) {
          container.current.addEventListener(
            "touchstart",
            onStartTouch as any,
            false
          );
          container.current.addEventListener(
            "touchmove",
            onMoveTouch as any,
            false
          );
          container.current.addEventListener(
            "touchend",
            onEndTouch as any,
            false
          );
  
          return () => {
            if (container.current) {
              container.current.removeEventListener(
                "touchstart",
                onStartTouch as any,
                false
              );
              container.current.removeEventListener(
                "touchmove",
                onMoveTouch as any,
                false
              );
              container.current.removeEventListener(
                "touchend",
                onEndTouch as any,
                false
              );
            }
          };
        }
      }, []);
  
      const classes = classNames(`${prefix}-carousel`, className);
      // 进行swipeItems clone
      swipeItems = React.Children.map(
        children as React.ReactChild,
        (item: React.ReactChild, index: number) => (
          <div
            className={`${prefix}-carousel-item`}
            key={index + 1}
            style={{ width: carouselWidth + "px" }}
          >
            {item}
          </div>
        )
      );
      if (count !== 0) {
        swipeItems.push(
          <div
            className={`${prefix}-carousel-item`}
            key={count + 1}
            style={{ width: carouselWidth + "px" }}
          >
            {childrenArr[0]}
          </div>
        );
        swipeItems.unshift(
          <div
            className={`${prefix}-carousel-item`}
            key={0}
            style={{ width: carouselWidth + "px" }}
          >
            {childrenArr[count - 1]}
          </div>
        );
      }
      // 左右键
      const renderIndicator = () => {
        if (indicator) return indicator;
        return (
          <>
            <div
              className={`${prefix}-carousel-indicator ${prefix}-indicator-left`}
              onClick={prev}
            >
              <i className={`${prefix}-indicator-icon`}></i>
            </div>
            <div
              className={`${prefix}-carousel-indicator ${prefix}-indicator-right`}
              onClick={next}
            >
              <i className={`${prefix}-indicator-icon`}></i>
            </div>
          </>
        );
      };
      // 底部的提示
      const renderDots = () => {
        if (dots) return dots;
        return (
          <div className={`${prefix}-dots-wrap`}>
            {Array(...Array(count)).map((item, key) => (
              <div
                key={key}
                onClick={() => moveTo(key)}
                className={classNames(`${prefix}-dots`, {
                  [`${prefix}-dots-active`]: key === active
                })}
              >
                {item}
              </div>
            ))}
          </div>
        );
      };
  
      return (
        <div className={`${prefix}-carousel-wrap`} ref={ref as any}>
          <div ref={carouselContainerRef as any} className={classes} style={style}>
            <div
              style={swipeStyle}
              className={`${prefix}-carousel-container`}
              ref={container as any}
            >
              {swipeItems}
            </div>
            {showIndicators ? renderIndicator() : null}
            {showDots ? renderDots() : null}
          </div>
        </div>
      );
    }
  );
  
  export default Carousel;
  