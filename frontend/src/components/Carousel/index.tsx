import type { ICarouselConfigType, ICarouselProps } from "./interface";

import {
  BaseSyntheticEvent,
  Children,
  CSSProperties,
  FC,
  useCallback,
  useEffect,
  useRef,
} from "react";

import useInterval from "../../hooks/useInterval";
import useReactive from "../../hooks/useReactive";
import { DEFAULT_CONGIG } from "./consts";

import "./index.scss";
import Loading from "./Loading";

const Carousel: FC<ICarouselProps> = (props) => {
  const {
    children,
    time = DEFAULT_CONGIG.time,
    autoplay = DEFAULT_CONGIG.autoplay,
    onChange,
  } = props;

  /** 获取children长度 */
  const childrenArr = Children.toArray(children);
  const childrenL = Children.count(children);

  /** 跑马灯的dom对象 */
  const notableRef = useRef(null);

  /** 跑马灯配置 */
  const carouselConfig = useReactive<ICarouselConfigType>({
    isLeftBorder: true,
    isRightBorder: false,
    currentIndex: 0,
    baseOffset: 100,
  });

  /** style */
  const carouselStyle = useReactive<CSSProperties>({
    left: `-${carouselConfig.baseOffset}%`,
    transition: "all 1s",
  });

  const [setIntervalRF, clearIntervalRF] = useInterval(move, time);

  useEffect(() => {
    if (autoplay) {
      start();
      return stop;
    }
  }, []);

  /**开始自动播放 */
  function start() {
    if (autoplay) {
      setIntervalRF();
    }
  }

  /** 停止播放 */
  function stop() {
    if (autoplay) {
      clearIntervalRF();
    }
  }

  /** 去到第一张图 */
  function toBegin() {
    carouselStyle.transition = "none";
    carouselStyle.left = 0;
    setTimeout(() => {
      carouselStyle.transition = "all 1s";
      carouselStyle.left = "-100%";
    });
    carouselConfig.currentIndex = 0;
  }

  // 去到最后一张图
  function toEnd() {
    carouselStyle.left = `-${(childrenL + 2) * 100}%`;
    carouselStyle.transition = "none";
    setTimeout(() => {
      carouselStyle.transition = "all 1s";
      carouselStyle.left = `-${(childrenL + 1) * 100}%`;
    });
    carouselConfig.currentIndex = childrenL;
  }

  /** 控制图片切换，参数offIdx为要偏移的坐标量，默认向左偏移一个坐标量 */
  function move(offIdx = 1) {
    onChange?.(offIdx);
    if (carouselConfig.currentIndex === childrenL - 1 && offIdx > 0) {
      return toBegin();
    }
    if (carouselConfig.currentIndex === 0 && offIdx < 0) {
      return toEnd();
    }
    carouselConfig.currentIndex += offIdx;

    carouselStyle.left =
      -carouselConfig.baseOffset * carouselConfig.currentIndex - 100 + "%";
  }

  /** select 选中某一项，事件委托 */
  function handleSelectBanner(e: BaseSyntheticEvent) {
    // 先清除定时器
    stop();
    let currentDom = null;
    currentDom = e.target;

    if (currentDom.tagName !== "LI") return;

    let i = 0;

    // 找到当前点击位置对应的下标
    while (currentDom != null) {
      currentDom = currentDom.previousSibling;
      i += 1;
    }
    //偏移的坐标量
    let offsetIdx = i - 1 - carouselConfig.currentIndex;
    move(offsetIdx);
    // 点击后重新开启
    start();
  }

  /** 渲染li select */
  const renderSelectList = useCallback(
    () =>
      new Array(3).fill(0).map((_, idx) => {
        const isCur = carouselConfig.currentIndex === idx;
        return (
          <li key={idx} className={isCur ? "current-select" : ""}>
            {isCur ? <Loading time={autoplay ? time : 0} /> : ""}
          </li>
        );
      }),
    [autoplay, carouselConfig.currentIndex, time]
  );

  /** 渲染子元素 */
  const renderChildren = useCallback(
    () => (
      <>
        {childrenArr[childrenL - 1]}
        {children}
        {childrenArr[0]}
      </>
    ),
    [children, childrenArr, childrenL]
  );

  return (
    <div className="container">
      <div
        className="notable"
        ref={notableRef}
        style={{
          left: carouselStyle.left,
          transition: carouselStyle.transition,
        }}
      >
        {renderChildren()}
      </div>
      <ul className="select" onClick={handleSelectBanner}>
        {renderSelectList()}
      </ul>
    </div>
  );
};

export default Carousel;
