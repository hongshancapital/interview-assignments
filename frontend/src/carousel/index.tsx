import { Slide } from "./component/slide";
import React, { FC, Key, useCallback, useMemo, useState } from "react";
import { CarouselProps } from "./type";
import { Indicators } from "./component/indicators";
import styles from "./style.module.scss";
import { useInterval } from "./hooks/useInterval";
import { checkIdUniq } from "./utlis/checkIdUniq";

export const Carousel: FC<CarouselProps> = ({
  data,
  autoplay = true,
  delay = 3000,
  width = 600,
  height = 400,
}) => {
  const slideIndexToId = useMemo(
    () => Object.fromEntries(data.map(({ id }, index) => [index, id])),
    [data]
  );

  const slideIdToIndex = useMemo(
    () => Object.fromEntries(data.map(({ id }, index) => [id, index])),
    [data]
  );

  if (checkIdUniq(data)) {
    console.error("警告：id有重复字段，请检查！");
  }

  const [activeIndex, setActiveIndex] = useState(0);
  const [forceRefreshToken, setForceRefreshToken] = useState(0);

  const handleClick = useCallback(
    (id: Key) => {
      setActiveIndex(slideIdToIndex[id]);
      setForceRefreshToken(forceRefreshToken + 1);
    },
    [slideIdToIndex, forceRefreshToken]
  );

  const changeActiveIndex = useCallback(() => {
    if (activeIndex === data.length - 1) {
      setActiveIndex(0);
    } else {
      setActiveIndex(activeIndex + 1);
    }
    if (forceRefreshToken > 0) {
      setForceRefreshToken(0);
    }
  }, [activeIndex, data.length, forceRefreshToken]);

  useInterval(
    changeActiveIndex,
    autoplay && data.length > 1 ? delay : null,
    forceRefreshToken
  );

  return (
    <div style={{ width, height }} className={styles["carousel-container"]}>
      <div
        className={styles["carousel-transform-container"]}
        style={{ transform: `translateX(${-activeIndex * 100}%)` }}
      >
        {data.map((item) => (
          <Slide {...item} key={item.id} />
        ))}
      </div>

      <Indicators
        // 当仅有一条数据时，不进行播放
        autoplay={data.length <= 1 ? false : autoplay}
        delay={delay}
        data={data}
        activeSlideId={slideIndexToId[activeIndex]}
        onClick={handleClick}
      />
    </div>
  );
};
