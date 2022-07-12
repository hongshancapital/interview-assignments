import React, { useMemo } from "react";
import "./Slides.css";
import { TSlide } from "./carousel-types";

interface TSlidesProps {
  slides: Array<TSlide>;
  currentSlide: number;
  width: number;
}

interface TSlideProps {
  slide: TSlide;
  width: number;
}

function Slide(props: TSlideProps) {
  const { slide, width } = props;
  const styleObj = useMemo(
    () => ({
      backgroundImage: `url(${slide.img})`,
      backgroundColor: slide.backgroundColor,
      backgroundSize: `${slide.imgWidth}px ${slide.imgHeight}px`,
      width: `${width}px`,
      color: slide.textColor,
    }),
    [width]
  );

  return (
    <div className="slide" style={styleObj}>
      <div
        className="title"
        dangerouslySetInnerHTML={{ __html: slide.title }}
      ></div>
      {slide.desc && (
        <div
          className="text"
          dangerouslySetInnerHTML={{ __html: slide.desc }}
        ></div>
      )}
    </div>
  );
}

function Slides(props: TSlidesProps) {
  const { slides, currentSlide, width } = props;

  const styleObj = useMemo(() => {
    const transform = `translate3d(-${currentSlide * width}px, 0px, 0px)`;
    const transition = `transform 500ms ease 0s`;

    return {
      width: `${slides.length * width}px`,
      WebkitTransform: transform,
      MozTransform: transform,
      msTransform: transform,
      OTransform: transform,
      transform: transform,
      WebkitTransition: transition,
      MozTransition: transition,
      msTransition: transition,
      OTransition: transition,
      transition: transition,
    };
  }, [currentSlide, slides, width]);

  return (
    <div className="slides" style={styleObj}>
      {slides.map((item, index) => (
        <Slide slide={item} width={width} key={`slide_${index}`} />
      ))}
    </div>
  );
}

export default Slides;
