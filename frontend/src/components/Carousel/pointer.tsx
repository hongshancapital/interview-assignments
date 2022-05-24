import * as React from "react";
import classNames from "classnames";

export interface PointerProps {
  count: number;
  activeKey: number;
  duration: number;
  onChange: (key: number) => void;
}

const Pointer: React.FC<PointerProps> = ({
  count,
  activeKey,
  duration,
  onChange,
}) => {
  const pointerBoxStyle: React.CSSProperties = {
    gridTemplateColumns: `repeat(${count}, ${(100 / count).toFixed(2)}%)`,
  };

  const renderPointerItems = () => {
    return Array.from({ length: count }, (x, i) => i).map((i) => (
      <div
        className="carousel-pointer-item"
        onClick={() => onChange(i)}
        key={i}
      >
        <div
          className={classNames("carousel-pointer-item-inner", {
            "carousel-pointer-item-inner_active": i === activeKey,
          })}
          style={{
            transitionDuration: `${duration / 1000}s`,
          }}
        />
      </div>
    ));
  };
  return (
    <div className="carousel-pointer">
      <div className="carousel-pointer-box" style={pointerBoxStyle}>
        {renderPointerItems()}
      </div>
    </div>
  );
};

export default Pointer;
