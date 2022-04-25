import React, { useState } from "react";
import "./Slide.scss";

export interface AbstractSlideProps {
  bgColor: string;
  color: string;
  imgTop: string;
  img: string;
  paragraph: string;
}

export const AbstractSlide = (props: AbstractSlideProps) => {
  const [styleO] = useState(() => ({
    backgroundColor: props.bgColor,
    color: props.color,
  }));

  return (
    <div className="slideContent" style={styleO}>
      <div className="slideContent__bg" style={{ top: props.imgTop }}>
        <img alt="tablet" src={props.img} />
      </div>
      <div
        className="slideContent__paragraph"
        dangerouslySetInnerHTML={{
          __html: props.paragraph,
        }}
      />
    </div>
  );
};
