import React, {
  useState,
  useEffect,
  useMemo,
  useRef,
  useLayoutEffect,
  PropsWithChildren,
} from "react";

import "./Swiper.css";
interface Props {
  perStayTime: number;
  perAniTime: number;
  initcialIndex: number;
  autoPlay?: boolean;
}

const Swiper: React.ForwardRefExoticComponent<
  PropsWithChildren<Props> & React.RefAttributes<any>
> = React.forwardRef((props, externalElRef) => {
  const { perStayTime, perAniTime, children, autoPlay } = props;
  const [current, setCurrent] = useState(props.initcialIndex);
  const timer = useRef<number>();
  const swiperElRef = useRef(null);

  const count = useMemo(() => {
    return React.Children.count(children);
  }, [children]);

  useEffect(() => {
    if (autoPlay) {
      // 每一次副作用开启一次定时器，实现循环轮播
      // 如果有点击切换，记得清楚此定时器后在 setCurrent
      timer.current = window.setTimeout(() => {
        setCurrent((current + 1) % count);
      }, perStayTime + perAniTime);

      return () => {
        console.log(11);
        clearTimeout(timer.current);
      };
    }
  }, [current, perStayTime, perAniTime, count, autoPlay]);

  useLayoutEffect(() => {
    //抛出ref
    if (externalElRef) {
      if (typeof externalElRef === "object") {
        externalElRef.current = swiperElRef.current;
      } else {
        externalElRef(swiperElRef);
      }
    }
  });

  return (
    <div className="swiper" ref={swiperElRef}>
      <div
        className="swiper__wrapper"
        style={{
          transform: `translateX(-${100 * current}%)`,
          transition: `transform ${perAniTime / 1000}s linear`,
        }}
      >
        {props.children}
      </div>

      {/* 这里pagination 当作内置组件了 */}
      <div className="swiper__pagination__bar">
        <ul className="swiper__pagination">
          {React.Children.map(props.children, (c, i) => (
            <li className="swiper__pagination__item">
              <span
                className={`swiper__pagination__item__fill ${
                  current > i ? "actived" : ""
                }`}
                style={
                  current === i
                    ? {
                        animation: `slideRight ${perStayTime / 1000}s forwards`,
                      }
                    : {}
                }
              ></span>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
});

export default Swiper;
