import "./index.css";

import { IndicatorProps } from "./types";
import { FunctionComponent, useCallback } from "react";

const Index: FunctionComponent<IndicatorProps> = props => {
  const { size, speed, currentIndex } = props;
  const renderItems = useCallback(() => {
    if (!size) return [];
    return Array.from({ length: size }).map((item, index) => {
      return (
        <li key={index} className="indicators_label">
          <span
            className="indicators_span"
            style={{
              animation:
                currentIndex === index ? `move ${speed}ms infinite` : "none",
            }}
          ></span>
        </li>
      );
    });
  }, [currentIndex, size, speed]);
  return <ul className="indicators">{renderItems()}</ul>;
};

export default Index;
