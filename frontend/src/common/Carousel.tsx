import React, { useEffect, useState } from "react";
import styles from "./carousel.module.scss";

interface Props {
  pages: JSX.Element[];
  pageWidth: number;
  timeInterval: number;
}

export default function Carousel(props: Props) {
  const [activeindex, setActiveindex] = useState<number>(-1);

  useEffect(() => {
    let index = 0;
    setActiveindex(0);
    let interval = setInterval(() => {
      index++;
      setActiveindex(index % props.pages.length);
      console.log(index % props.pages.length);
    }, props.timeInterval);
    return () => {
      clearInterval(interval);
    };
  }, []);

  let liWidth = props.pageWidth + "px";

  return (
    <ul
      style={{ width: props.pages.length * props.pageWidth + "px" }}
      className={styles["list"]}
    >
      {props.pages.map((item, i) => {
        let translateX = -activeindex * props.pageWidth;
        return (
          <li
            key={i}
            style={{
              width: liWidth,
              transform: `translateZ(0px) translateX(${translateX}px)`,
            }}
          >
            {item}
          </li>
        );
      })}
      <div className={styles["across"]}>
        {props.pages.map((item, index) => {
          let style: React.CSSProperties = {};
          if (activeindex === index) {
            style.width = "100%";
            style.transition = `width ${props.timeInterval}ms ease 0s`;
          }
          if (activeindex > index) {
            style.width = "100%";
          }
          if (activeindex < index) {
            style.width = "0px";
          }
          return (
            <div className={styles["item"]}>
              <div className={styles["progress"]} style={style}></div>
            </div>
          );
        })}
      </div>
    </ul>
  );
}
