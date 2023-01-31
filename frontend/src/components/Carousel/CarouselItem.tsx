import React, { memo } from "react";
import classNames from "classnames";

export interface ICarouselItemProps {
  title?: string | React.ReactNode;
  description?: string | React.ReactNode;
  src?: string;
  alt?: string;
  id: string | number;
  className?: string;
  style?: React.CSSProperties;
}

const CarouselItem: React.FC<ICarouselItemProps> = memo((props) => {
  const { title, description, id, style, className: classNameProps } = props;
  const className = classNames("carousel-item", classNameProps);

  return (
    <li style={style} key={id} className={className}>
      <div style={{ width: "100vw" }}>
        <h1 className="title">{title}</h1>
        <p className="text"> {description} </p>
      </div>
    </li>
  );
});

export default CarouselItem;
