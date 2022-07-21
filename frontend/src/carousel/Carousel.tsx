import Page from "./components/Page";
import data from "./data";
import styles from "./carousel.module.sass";
import { useEffect, useRef, useState } from "react";

const CAROUSEL_TIME = 3000;

function Carousel() {
  function calcStyle(i: number, index: number) {
    return {
      transform: `translate(${(index - i) * widthRef.current}px)`,
    };
  }

  function resizeWidth() {
    widthRef.current = document.body.clientWidth;
  }

  const [index, setIndex] = useState(0);
  const timerRef = useRef<NodeJS.Timer>();
  const widthRef = useRef(document.body.clientWidth);
  const length = data.length;

  useEffect(() => {
    window.addEventListener("resize", resizeWidth);
    return () => {
      window.removeEventListener("resize", resizeWidth);
    };
  }, []);

  // useEffect(() => {
  //   timerRef.current = setInterval(() => {
  //     setIndex((prev) => (prev + 1 >= length ? 0 : prev + 1));
  //   }, CAROUSEL_TIME);
  //   return () => {
  //     timerRef.current && clearInterval(timerRef.current);
  //   };
  // }, [length]);

  return (
    <div className={styles.container}>
      {data.map((d, i) => {
        const { style, ...other } = d;
        const s = Object.assign({}, style, calcStyle(index, i));
        return <Page key={d.id} {...other} style={s} />;
      })}
    </div>
  );
}

export default Carousel;
