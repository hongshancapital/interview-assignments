import {
  CSSProperties,
  FC,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";

import "./index.css";

import Banner, { BannerProp_Inter } from "./Banner";
import Indicator from "./Indicator";

export interface Banner_Inter extends BannerProp_Inter {
  id: number;
}

interface CarouselProps_Inter {
  /** 轮播列表 */
  list: Banner_Inter[];
  /** 单个轮播图停留时间，单位ms */
  period?: number;
}

const Carousel: FC<CarouselProps_Inter> = ({ list = [], period = 3000 }) => {
  const intervalRef = useRef<number>();

  /** 当前播放的轮播图索引 */
  const [currentIndex, setCurrentIndex] = useState<number>(-1);

  /** 索引变更 */
  const next = useCallback(() => {
    const newIndex = currentIndex < list.length - 1 ? currentIndex + 1 : 0;
    setCurrentIndex(newIndex);
  }, [currentIndex, list.length]);

  /** 初始化 */
  useEffect(() => setCurrentIndex(0), []);

  /** 开始计时 */
  useEffect(() => {
    //                       ↓  此处"window."避免编译阶段将setTimeout返回值认定为Nodejs环境的Timeout类型而报错
    intervalRef.current = window.setTimeout(next, period);
    return () => clearTimeout(intervalRef.current);
  }, [next, period]);

  /** 轮播图队列定位控制 */
  const bannerListstyle: CSSProperties = useMemo(
    () => ({
      left: `-${currentIndex}00%`,
    }),
    [currentIndex]
  );

  /** 轮播图队列 */
  const banners = useMemo(
    () =>
      list.map(({ id, ...info }, index) => (
        <Banner {...info} key={id} index={index} />
      )),
    [list]
  );
  /** 指示器队列 */
  const indicators = useMemo(
    () =>
      list.map(({ id }, index) => (
        <Indicator key={id} active={index === currentIndex} period={period} />
      )),
    [list, currentIndex, period]
  );

  return (
    <div className="carousel-control">
      <div
        className="banner-list-control"
        style={bannerListstyle}
      >
        {banners}
      </div>
      <div className="indiacator-list-control">{indicators}</div>
    </div>
  );
};

export default Carousel;
