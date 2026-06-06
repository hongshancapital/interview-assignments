import React from "react";

interface Context {
  go(index: number): void;
  duration: number;
  activeIndex: number;
}

const CarouselContext = React.createContext({} as Context);
export default CarouselContext;
