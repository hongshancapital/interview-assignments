import React, { useEffect, useState } from "react";
import Progress from "../Progress";
import "./pagination.css";

export interface PaginationProps {
  size: number;
  timeout?: number;
  onActiveChange: (i: number) => void;
  style?: React.CSSProperties;
}

const Pagination: React.FC<PaginationProps> = (props: PaginationProps) => {
  const { size, timeout, style, onActiveChange } = props || {};
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    onActiveChange(activeIndex);
  }, [activeIndex, onActiveChange]);

  const render = () => {
    if (size === 0) {
      return null;
    }
    const items = [];
    for (let i = 0; i < size; ++i) {
      items.push(
        <div className="pagination-item" key={i}>
          <Progress
            activeIndex={activeIndex}
            index={i}
            timeout={timeout}
            onTimeout={(i) => setActiveIndex((i + 1) % size)}
          />
        </div>
      );
    }
    return items;
  };

  return (
    <div className="pagination-container" style={style}>
      {render()}
    </div>
  );
};

export default Pagination;
