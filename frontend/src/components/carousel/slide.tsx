import React, { FC } from "react";
import { ISlideProps } from "./type";
import "./slide.css";

const Slide: FC<ISlideProps> = ({
  title,
  description,
  img,
  backgroundColor,
  color = "#000",
}) => {
  return (
    <div className="slide" style={{ backgroundImage: `url(${img})`, color, backgroundColor, }}>
      <div className="slide-title">
        {title.map((item: string, index: number) => (
          <h1 key={index}>{item}</h1>
        ))}
      </div>
      {description?.length && (
        <div className="slide-description">
          {description.map((item: string, index: number) => (
            <h6 key={index}>{item}</h6>
          ))}
        </div>
      )}
    </div>
  );
};

export default Slide;
