import React, {
  ForwardRefExoticComponent,
  ReactNode,
  useLayoutEffect,
  useRef,
} from "react";
import "./CarouselItem.css";

type CarouselItemProps = {
  children: ReactNode;
  style?: React.CSSProperties;
  key?: string;
};

const CarouselItem: ForwardRefExoticComponent<CarouselItemProps> = React.forwardRef(
  (props, ref) => {
    const eleRef = useRef(null);

    useLayoutEffect(() => {
      if (ref) {
        if (typeof ref === "object") {
          ref.current = eleRef.current;
        } else {
          ref(eleRef);
        }
      }
    });

    return (
      <div
        className="carousel__item"
        style={props.style}
        key={props.key}
        ref={eleRef}
      >
        {props.children}
      </div>
    );
  }
);

export default CarouselItem;
