import CarouselItem from "components/CarouselItem";
import Progress from "components/Progress";
import React, { MouseEvent, useEffect, useState, useRef } from "react";
import "./styles.css";

function Carousel({ duration = 3000, data }: CarouselProps) {
  const itemLen = data.length;
  const [pLeft, setPLeft] = useState(0);
  const [Index, setIndex] = useState(0);
  let timer = useRef<NodeJS.Timer>();

  const onMouseIn = (event: MouseEvent<Element>) => {
    // console.log(time);
    // clearInterval(timer.current);
  };
  const renderItems = (data: CarouselItem[]) =>
    data.map((d, i) => <CarouselItem {...d} key={i} />);

  const renderProgress = (data: CarouselItem[], index: number) => (
    <Progress index={index} data={data} duration={duration} />
  );
  useEffect(() => {
    timer.current = setInterval(() => {
      if (Index === itemLen - 1) {
        setIndex(0);
      } else {
        setIndex(Index + 1);
      }
    }, duration);
    setPLeft(Index * 100);
    return () => {
      clearInterval(timer.current);
    };
  }, [Index, duration, itemLen]);
  return (
    <div className="carousel" onMouseEnter={onMouseIn}>
      <div
        className="carousel-warp"
        style={{ width: itemLen * 100 + "%", left: -pLeft + "%" }}
      >
        {renderItems(data)}
      </div>
      {renderProgress(data, Index)}
    </div>
  );
}

export default Carousel;
