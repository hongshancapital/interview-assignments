import React from "react";

import { Slide as SlideProps } from "../../../../types";

import "./index.css";

export default function Slide(props: SlideProps) {
  const { title, content, image, color, backgroundColor } = props;

  return (
    <div
      className="slide-wrapper"
      style={{
        backgroundColor,
        color,
        backgroundImage: `url(${image})`,
      }}
    >
      {
        title.map((item, index) => <h2 className="title" key={item + index}>{item}</h2>)
      }
      {
        content.map((item, index) => <p className="content" key={item + index}>{item}</p>)
      }
    </div>
  );
}
