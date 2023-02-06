import { ReactNode } from "react";
import "../css/CarouselItem.css";
import { type CarouselSize, SizeContext } from "./Carousel";

interface CarouselItemProps {
  mode: "dark" | "light";
  children: ReactNode[] | ReactNode;
}

export function CarouselItem(props: CarouselItemProps) {
  return (
    <SizeContext.Consumer>
      {(size: CarouselSize) => (
        <div
          className={`carousel-item ${props.mode}`}
          style={{
            ...size,
          }}
        >
          {props.children}
        </div>
      )}
    </SizeContext.Consumer>
  );
}
