import React, {
  useState,
  useEffect,
  useMemo,
  useRef,
  useLayoutEffect,
  PropsWithChildren,
  FunctionComponentElement,
} from "react";

import "./Carousel.css";

import CarouselIndicator from "./CarouselIndicator";
import CarouselItem from "./CarouselItem";

interface Props {
  perStayTime: number;
  perAniTime: number;
  initcialIndex: number;
  autoPlay?: boolean;
}

const Carousel: React.ForwardRefExoticComponent<
  PropsWithChildren<Props> & React.RefAttributes<any>
> = React.forwardRef((props, externalElRef) => {
  const { perStayTime, perAniTime, children, autoPlay } = props;
  const [current, setCurrent] = useState(props.initcialIndex);
  const timer = useRef<number>();
  const carouselElRef = useRef(null);

  const count = useMemo(() => {
    return React.Children.count(children);
  }, [children]);

  useEffect(() => {
    if (autoPlay) {
      // 每一次副作用开启一次定时器，实现循环轮播
      timer.current = window.setTimeout(() => {
        setCurrent((current + 1) % count);
      }, perStayTime + perAniTime);

      return () => {
        clearTimeout(timer.current);
      };
    }
  }, [current, perStayTime, perAniTime, count, autoPlay]);

  useLayoutEffect(() => {
    //抛出ref
    if (externalElRef) {
      if (typeof externalElRef === "object") {
        externalElRef.current = carouselElRef.current;
      } else {
        externalElRef(carouselElRef);
      }
    }
  });


  const renderCarouselItem = () => {
    return React.Children.map(props.children, (child, index) => {
      //TODO 这里判断是否是carouselItem
      if (React.isValidElement(child)) {
        return child
      } else {
        return
      }
    })
  }

  return (
    <div className="carousel" ref={carouselElRef}>
      <div
        className="carousel__wrapper"
        style={{
          transform: `translateX(-${100 * current}%)`,
          transition: `transform ${perAniTime / 1000}s linear`,
        }}
      >
        {renderCarouselItem()}
      </div>

      <div className="carousel__indicator__bar">
        <CarouselIndicator current={current} onIndexChange={(i) => {setCurrent(i) }} duration={perStayTime} count={count}></CarouselIndicator>
      </div>
    </div>
  );
});

export default Carousel;
