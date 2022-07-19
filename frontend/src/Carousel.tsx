import React, { useEffect, useState } from "react";
import "./index.css";
import list from "./config";
import { ListProps } from "./interface";

const Carousel = () => {
  const [count, setCount] = useState(0);
  useEffect(() => {
    const timer = setInterval(() => {
      if (count > 1) {
        setCount(0);
      } else {
        setCount(count + 1);
      }
    }, 2000);
    return () => clearInterval(timer);
  }, [count]);

  return (
    <div className="box">
      <ul className="imgs" style={{ marginLeft: -count * 375 }}>
        {list.map((el: ListProps) => {
          return (
            <li className="item" key={el.url}>
              <img src={el.url} alt="" />
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default Carousel;
