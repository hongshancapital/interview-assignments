import React from "react";

export interface CarouselItemProps {
  /** 定制子组件的样式 */
  className?: string;
  /** 如果全局的dot样式不合适，则此处可以为本图替换style */
  dotClassName?: string;
  /** 如果全局的dot样式不合适，则此处可以为本图替换style */
  dotCurrentClassName?: string;
}

const CarouselItem: React.FC<CarouselItemProps> = (props) => {
  return (
    <div className="carousel-item">
      <div className={props.className}>{props.children}</div>
    </div>
  );
};

CarouselItem.displayName = "CarouselItem";

CarouselItem.defaultProps = {
  className: "",
  dotClassName: "",
  dotCurrentClassName: "",
};

export { CarouselItem };
