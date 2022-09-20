import React, { useState, useCallback } from "react";
import ScrollBox from "./ScrollBox";
import classname from "classnames";
import type { CarouselProps } from "../../type";
import useInterval from "../../hooks/useInterval";
import scssStyle from "./index.module.scss";

const Carousel = ({ dataList, delay = 3000 }: CarouselProps) => {
  const [currentInd, currentIndHandle] = useState(0);

  const bannerChange = useCallback(() => {
    currentIndHandle(draft => {
      return draft > 1 ? 0 : draft + 1;
    });
  }, []);

  const [isStop, stopHandle] = useInterval(bannerChange, delay);

  const move = (ind: number) => {
    currentIndHandle(ind);
    stopHandle(true);
  };

  if (!dataList.length) {
    return <div>dataList 不能为空</div>;
  }

  return (
    <div className={scssStyle.container}>
      <ScrollBox currentInd={currentInd} dataList={dataList} />

      <div className={scssStyle.control_box}>
        {dataList.map((e, i) => (
          <div
            key={e.key}
            className={scssStyle.item}
            onClick={() => {
              move(i);
            }}
            onMouseLeave={() => {
              if (!isStop || currentInd !== i) return;
              stopHandle(false);
            }}
          >
            <div
              className={classname({
                [scssStyle.color_block]: true,
                [scssStyle.color_block_animation]: !isStop && currentInd === i,
                [scssStyle.color_block_stop]: isStop && currentInd === i,
              })}
              style={{ animationDuration: delay + "ms" }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
