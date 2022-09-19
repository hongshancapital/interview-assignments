import React, { CSSProperties } from "react";
import { PREFIX_CLS } from "./constans";

interface CarouselItemProps {
  styles?: CSSProperties;
  width?: string;
  height?: string;
}

export const CarouselItem: React.FC<CarouselItemProps> = ({
  width = "100%",
  height = "100%",
  styles = {},
  children,
}) => {
  return (
    <div className={`${PREFIX_CLS}-item`} style={{ width, height, ...styles }}>
      {children}
    </div>
  );
};
