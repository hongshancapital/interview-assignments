import React, { useEffect, useState } from "react";
import "./Carousel.scss";

export interface SlideItem {
  id: string;
  content: React.ReactNode;
}

export const Carousel = (props: { slides: SlideItem[]; timingDur: number }) => {
  const [curr, setCurr] = useState(0);
  const total = props.slides.length;
  const translatePercent = ((curr * 100) / total).toFixed(3);

  const styleForSlides: React.CSSProperties = {
    transform: `translate(-${translatePercent}%, 0)`,
  };

  const nextSlide = (index?: number) => {
    if (typeof index === "number") {
      setCurr(index);
    } else {
      setCurr((curr + 1) % total);
    }
  };

  return (
    <div className="Carousel">
      <div className="Carousel__slidesWindow">
        <div className="Carousel__slides" style={styleForSlides}>
          {props.slides.map((slide) => {
            return <Slide key={slide.id} context={slide.content} />;
          })}
        </div>
      </div>
      <div className="Carousel__indicators">
        {props.slides.map((x, index) => {
          return (
            <Indicator
              key={x.id}
              timingDur={props.timingDur}
              active={index === curr}
              index={index}
              onTimeout={nextSlide}
              onItemClick={nextSlide}
            />
          );
        })}
      </div>
    </div>
  );
};

const Slide = (props: { context: React.ReactNode }) => {
  return <div className="Carousel__slide">{props.context}</div>;
};

const Indicator = (props: {
  index: number;
  active: boolean;
  timingDur: number;
  onTimeout: VoidFunction;
  onItemClick: (index: number) => void;
}) => {
  const { index, active } = props;
  const dur = props.timingDur / 1000;

  return (
    <a
      className="Carousel__indicator"
      onClick={() => {
        props.onItemClick(index);
      }}
    >
      {active && (
        <div
          className="Carousel__indicator-progress"
          onAnimationEnd={props.onTimeout}
          style={{ animationDuration: dur + "s" }}
        />
      )}
    </a>
  );
};
