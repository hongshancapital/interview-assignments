import React, { FC, memo } from "react";

const Carousel: FC = memo((props) => {
  return <div className="carousel-item">{props.children}</div>;
});

export default Carousel;
