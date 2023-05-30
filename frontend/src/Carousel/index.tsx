import ItemBar from "./ItemBar";
import "./index.scss";
import { Children, ReactNode, useEffect, useRef, useState } from "react";

interface Props {
  defaultCounter?: number;
  duration?: number;
  children?: ReactNode;
  width?: number;
  height?: number;
  callback?: () => void;
}

export default function Index({
  defaultCounter = 2,
  duration = 0.2,
  children,
  width = 1024,
  height = 768,
  callback,
}: Props) {
  const [current, setCurrent] = useState<number>(0);
  const timer = useRef<NodeJS.Timer>();
  const imgLength = Children.count(children);
  useEffect(() => {
    if (!timer.current) {
      timer.current = setInterval(() => {
        setCurrent((i) => {
          i += 1;
          callback && callback();
          if (i >= imgLength) {
            return 0;
          }
          return i;
        });
      }, defaultCounter * 1000);
    }
  }, [defaultCounter, imgLength]);
  return (
    <div
      className="carousel-container"
      style={{
        // @ts-ignore 屏蔽语法检查 因为 --counter是自己定义的
        "--counter": defaultCounter + "s",
        "--duration": duration + "s",
        "--item-width": `${width}px`,
        "--item-height": `${height}px`,
        width: width + "px",
        height: height + "px",
      }}
    >
      <div
        style={{
          position: "absolute",
          left: `${-current * width}px`,
          width: width * imgLength + "px",
          height: height + "px",
        }}
        className={"item-container"}
      >
        {children}
      </div>

      <div className="item-bar-container">
        {new Array(imgLength).fill(0).map((_, index) => (
          <ItemBar key={index} active={index === current} />
        ))}
      </div>
    </div>
  );
}
