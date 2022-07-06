import React from "react";

interface ICarouselDotItemProps {
  active: boolean;
  interval: number;
}

function CarouselDotItem(props: ICarouselDotItemProps) {
  const { active, interval } = props;
  return (
    <div
      className={`carousel-dots__item ${
        active ? "carousel-dots__item__active" : ""
      }`}
    >
      {active && (
        <div
          className="carousel-dots__item__move"
          style={{ animationDuration: `${interval}ms` }}
        />
      )}
    </div>
  );
}

export default CarouselDotItem;
