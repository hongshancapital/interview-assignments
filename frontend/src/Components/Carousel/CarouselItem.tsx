import React from "react";
import { CasItemProps } from "./Casrousel.types";
import "./styles/CasrouselItem.css";
const CasrouselItem: React.FC<CasItemProps> = (props) => {
  const {
    casrouseInfo: {
      title,
      subText,
      casrouselImage,
      titleStyles,
      subTextStyles,
    },
  } = props;
  return (
    <div
      className="casrousel-container"
      style={{
        backgroundImage: `url(${casrouselImage})`,
      }}
    >
      <div className="casrousel-title">
        {typeof title === "string" ? (
          <div style={titleStyles}>{title}</div>
        ) : (
          <span style={titleStyles}>{title}</span>
        )}
        {typeof subText === "string" ? (
          <div style={subTextStyles}>{subText}</div>
        ) : (
          <span style={subTextStyles}>{subText}</span>
        )}
      </div>
    </div>
  );
};

export default CasrouselItem;
