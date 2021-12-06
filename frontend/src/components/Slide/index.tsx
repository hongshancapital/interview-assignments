import React from "react";
import { Slide as SlideProps } from "../../types/Slide";
import "./index.css";

const Slide = (props: { data: SlideProps }) => {
  const slide = props.data;
  return (
    <div
      className="slide-wrapper"
      style={{
        backgroundColor: slide.backgroundColor,
        color: slide.color,
        backgroundImage: `url(${slide.image})`,
      }}
      key={slide.id}
    >
      {slide.title.map((title, index) => (
        <h2 className="title" key={title + index}>
          {title}
        </h2>
      ))}
      {slide.content.map((content, index) => (
        <p className="content" key={content + index}>
          {content}
        </p>
      ))}
     
    </div>
  );
};

export default Slide;
