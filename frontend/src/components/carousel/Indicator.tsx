import { useMemo } from "react";
import "./Indicator.css";

interface IndicatorProps {
  autoplay: boolean;
  currentIndex: number;
  childrenCount: number;
  previewDuration: number;
  itemWidth?: number;
  itemHeight?: number;
  bottomDistance?: number;
  spaceBetween?: number;
  onClick?: (index: number) => void;
}

const Indicator: React.FC<IndicatorProps> = ({
  autoplay,
  currentIndex,
  childrenCount,
  previewDuration,
  itemWidth = 40,
  itemHeight = 2,
  bottomDistance = 64,
  spaceBetween = 15,
  onClick,
}: IndicatorProps) => {
  const childrenArray = useMemo(() => {
    return new Array(childrenCount).fill(0);
  }, [childrenCount]);
  return (
    <div
      className="indicator-container"
      style={{
        width: `${
          childrenCount * itemWidth + (childrenCount - 1) * spaceBetween
        }px`,
        height: `${itemHeight}px`,
        bottom: `${bottomDistance}px`,
      }}
    >
      {childrenArray.map((_, i) => {
        return (
          <div
            key={i}
            style={{
              width: `${itemWidth}px`,
              height: `${itemHeight}px`,
              marginRight: `${
                i === childrenArray.length - 1 ? 0 : spaceBetween
              }px`,
              flexShrink: 0,
              position: "relative",
            }}
            onClick={() => {
              onClick?.(i);
            }}
            data-testid={`indicator-${i}`}
          >
            <div className="indicator-item-background" />
            <div
              className="indicator-item-foreground"
              style={{
                width: `${autoplay || i !== currentIndex ? "0px" : "100%"}`,
                animation: `${
                  autoplay && i === currentIndex
                    ? `indicatorItemAnim ${previewDuration}s`
                    : ""
                }`,
              }}
            ></div>
          </div>
        );
      })}
    </div>
  );
};

export default Indicator;
