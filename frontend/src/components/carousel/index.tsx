import React, { FC, useEffect, useRef, useState } from "react";

import airpds from "../../assets/airpods.png";
import iphone from "../../assets/iphone.png";
import tablet from "../../assets/tablet.png";
import "./index.css";

import BannerList, { Banner_Inter } from "./bannerList";
import IndicatorList from "./indicatorList";
import { Them_Enum } from "./bannerList/banner";
import { PERIOD } from "./config";

/**
 * 轮播图数据
 */
const bannerList: Banner_Inter[] = [
  {
    id: 1,
    imgUrl: iphone,
    backgroundColor: "#111111",
    title: "xPhone",
    text: "Lost to love. Less to spend. Starting at $399.",
    them: Them_Enum.dark,
  },
  {
    id: 2,
    imgUrl: tablet,
    backgroundColor: "#f9f9f9",
    title: "Tablet",
    text: "Just the right amount of everything.",
  },
  {
    id: 3,
    imgUrl: airpds,
    backgroundColor: "#f0f0f2",
    title: "Buy a Table or xPhone for college. Get arPods.",
  },
];

const Carousel: FC = () => {
  const intervalRef = useRef<number>();

  /** 当前播放的轮播图索引 */
  const [currentIndex, setCurrentIndex] = useState<number>();

  /** 索引变更 */
  const next = () => {
    const newIndex =
      currentIndex! < bannerList.length - 1 ? currentIndex! + 1 : 0;

    setCurrentIndex(newIndex);
  };

  /** 初始化 */
  useEffect(() => {
    setCurrentIndex(0);
  }, []);

  /** 开始计时 */
  useEffect(() => {
    intervalRef.current = Number(setTimeout(() => next(), PERIOD));

    return () => {
      clearTimeout(intervalRef.current);
    };
  });

  return (
    <div className="carousel-control" data-testid="carousel">
      <BannerList banners={bannerList} currentIndex={currentIndex} />
      <IndicatorList currentIndex={currentIndex} total={bannerList.length} />
    </div>
  );
};

export default Carousel;
