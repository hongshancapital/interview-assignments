import React from "react";
import classNames from "classnames";
import "./index.scss";

/**
 * The carousel indicator (at bottom of carousel)
 * @param {CarouselIndicatorProps} props {
 *   actived: false,  // whether the indicator is actived
 *   switchInterval: 3000,  // the switching interval (unit: ms)
 *   className: "",  // the class name of the indicator (use to overwrite default styles)
 * }
 */
function CarouselIndicator(props: CarouselIndicatorProps): JSX.Element {
  const {
    actived,
    switchInterval,
    className: customClassName,
  } = props;
  return (
    <div
      className={classNames("carousel-indicator", customClassName, {
        "carousel-indicator-actived": actived,
      })}
    >
      <div
        className="carousel-indicator-progress"
        style={{transitionDuration: `${switchInterval}ms`}}
      />
    </div>
  );
}

export default CarouselIndicator;
