import React, {
  CSSProperties,
  FC,
  ReactElement,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";

import "./index.css";

import Banner, { BannerProp_Inter } from "./Banner";
import Indicator from "./Indicator";

/** 单个轮播图停留时间，单位ms */
const PERIOD = 3000;

export interface Banner_Inter extends BannerProp_Inter {
  id: number;
}

export interface BannerListProps_Inter {
  banners: Banner_Inter[];
  currentIndex?: number;
}

export const Context = React.createContext({});

interface CarouselProps {
  list: Banner_Inter[];
}

const Carousel: FC<CarouselProps> = ({ list = [] }) => {
  const intervalRef = useRef<number>();

  /** 当前播放的轮播图索引 */
  const [currentIndex, setCurrentIndex] = useState<number>();

  /** 索引变更 */
  const next = useCallback(() => {
    const newIndex = currentIndex! < list.length - 1 ? currentIndex! + 1 : 0;

    setCurrentIndex(newIndex);
  }, [currentIndex, list.length]);

  /** 初始化 */
  useEffect(() => setCurrentIndex(0), []);

  /** 开始计时 */
  useEffect(() => {
    intervalRef.current = Number(setTimeout(next, PERIOD));

    return () => clearTimeout(intervalRef.current);
  }, [next]);

  const style: CSSProperties = {
    left: `-${currentIndex}00%`,
  };

  const banners = useMemo(
    () =>
      list.map(({ id, ...info }, index) => (
        <Banner {...info} key={id} index={index} />
      )),
    [list]
  );
  const indicators = useMemo(
    () =>
      list.map(({ id }, index) => (
        <Indicator key={id} active={index === currentIndex} />
      )),
    [list, currentIndex]
  );

  return (
    <div className="carousel-control" data-testid="carousel">
      <div
        data-testid="banner-list"
        className="banner-list-control"
        style={style}
      >
        {banners}
      </div>
      <div className="indiacator-list-control">{indicators}</div>
    </div>
  );
};

export default Carousel;
