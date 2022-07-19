import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import "./Carousel.css";

interface Props {
  children: Array<React.ReactElement>;
  loop?: boolean;
}

export default function Carousel(props: Props) {
  const { children, loop } = props;
  // 当前坐标
  const [currentCarousel, setCurrentCarousel] = useState(0);
  const [realCarousel, setRealCarousel] = useState(-1);
  // 动画时间
  // const [animationStep, setAnimationStep] = useState(0);

  // 最新值存储
  const curRef = useRef(0);

  // 子节点个数
  const len = useMemo(() => React.Children.count(children), [children]);

  const realLen = loop ? len + 2 : len;
  const resetLen = loop ? len + 1 : len;
  const listStyle = {
    // transition: `transform ${animationStep}s`,
    width: `${realLen * 100}%`,
    // transform: `translateX(${
    //   (-100 / realLen) * (currentCarousel + (loop ? 1 : 0))
    // }%)`,
  };

  const itemStyle = { width: `${100 / realLen}%` };

  const timerID = useRef<NodeJS.Timer>();

  const handlerCarousel = useCallback(
    (type: string) => {
      let direction: number = 1;
      // 判断方向
      if (type === "left") {
        direction = -1;
      }

      if (curRef.current % resetLen !== len && curRef.current >= 0) {
        // 更新位置
        setCurrentCarousel((curRef.current + direction) % resetLen);
        // setAnimationStep(1);
      }
    },
    [len, resetLen]
  );

  // 监听动画结束
  const handlerTransitionEnd = () => {
    if (currentCarousel % resetLen === len) {
      // 末端重置
      // setAnimationStep(0);
      setCurrentCarousel(0);
    } else if (currentCarousel < 0) {
      // 开端重置
      // setAnimationStep(0);
      setCurrentCarousel(len - 1);
    }
  };

  const stopCarousel = () => {
    timerID.current && clearInterval(timerID.current);
  };

  const startCarousel = useCallback(() => {
    stopCarousel();
    timerID.current = setInterval(() => {
      handlerCarousel("right");
    }, 2000);
  }, [handlerCarousel]);

  // const handlerNext = () => {
  // 	handlerCarousel('right');
  // };

  // const handlerPre = () => {
  // 	handlerCarousel('left');
  // };

  useEffect(() => {
    // setAnimationStep(1);
    startCarousel();
  }, [startCarousel]);

  // 获取最新值
  useEffect(() => {
    curRef.current = currentCarousel;
    setRealCarousel(currentCarousel % len);
  }, [currentCarousel, len]);

  return (
    <div className="carousel-container">
      <div
        className={`carousel-list carousel-position${realCarousel}`}
        onTransitionEnd={handlerTransitionEnd}
        style={listStyle}
      >
        {loop && (
          <div className="carousel-item" style={itemStyle} key={"start"}>
            {children[len - 1]}
          </div>
        )}
        {children.map((item, index) => {
          return (
            <div className="carousel-item" style={itemStyle} key={index}>
              {item}
            </div>
          );
        })}
        {loop && (
          <div className="carousel-item" style={itemStyle} key={"end"}>
            {children[0]}
          </div>
        )}
      </div>
      <div className="progress-container">
        {children.map((item, index) => {
          return (
            <div className="progress" key={index}>
              <div
                className={`progress-content ${
                  index === realCarousel ? "progress-done" : ""
                }`}
              ></div>
            </div>
          );
        })}
      </div>

      {/* <div>
        <div onClick={handlerPre}>{"<"}</div>
        <div onClick={handlerNext}>{">"}</div>
      </div> */}
    </div>
  );
}
