import React, { FC, ReactElement, useEffect, useMemo } from "react";
import { Indicator } from "./Indicator";
import { CarouselItem } from "./Item";

export { Indicator } from "./Indicator";
export type { IIndicatorProps } from "./Indicator";
export { CarouselItem } from "./Item";
export type { ICarouselItemProps } from "./Item";

type CarouselItemNode = ReactElement<typeof CarouselItem>;
type IndicatorNode = ReactElement<typeof Indicator>;
export interface ICarouselProps {}

export const Carousel: FC<ICarouselProps> = ({ children }) => {
  const { items, indicator } = useMemo<{ items: CarouselItemNode[]; indicator: IndicatorNode | null }>(() => {
    if (Object.prototype.toString.call(children) === "[object Array]") {
      const childList = children as (CarouselItemNode | IndicatorNode)[];
      const items = childList.filter((item) => item.type === CarouselItem);
      const indicator = childList.find((item) => item.type === Indicator) ?? null;
      return { items, indicator };
    } else if (children && typeof children === "object" && children.hasOwnProperty("type")) {
      if ((children as ReactElement).type === CarouselItem) {
        return { items: [children as CarouselItemNode], indicator: null };
      }
    }
    return { items: [], indicator: null };
  }, [children]);

  useEffect(() => {
    console.log(items, indicator);
  }, [items, indicator]);

  return (
    <div className="carousel">
      {items}
      {indicator}
    </div>
  );
};
