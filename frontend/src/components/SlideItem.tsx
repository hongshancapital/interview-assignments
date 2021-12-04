import React from "react";
import { SlideType } from "../types";

const Slide = ({
  id, 
  title, 
  text = '', 
  description = '',
  img
} : SlideType) => {
  return (
    <div className="slide" data-testid="slide">
      <img className="slide-img" src={img} alt={title} />
      <div className="slide-title color">{title}</div>
      <p className="slide-text color">{text}</p>
      <p className="slide-text color">{description}</p>
    </div>
  );
}

export default React.memo(Slide);
