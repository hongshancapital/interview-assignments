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
  	<div className="slide">
  		<img className="slide-img" src={img} alt={title} />
  		<div className="slide-title color">{title}</div>
  		<p className="slide-text color">{text}</p>
  		<p className="slide-text color">{description}</p>
  	</div>
  );
}

export default Slide;
