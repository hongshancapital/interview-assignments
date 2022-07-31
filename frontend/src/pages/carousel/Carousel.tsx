import Page from "./components/Page";
import Progress from "./components/Progress";
import data from "@/assets/data/data";
import { INDEX } from "@/constants/const";
import styles from "./carousel.module.sass";
import { useEffect, useRef, useState } from "react";

function Carousel() {
  function calcStyle(i: number, index: number) {
    return {
      transform: `translate(${(index - i) * widthRef.current}px)`,
    };
  }

  function resizeWidth() {
    widthRef.current = document.body.clientWidth;
  }

  function changeActivePage(index: number) {
    setIndex(index);
  }

  const [index, setIndex] = useState(INDEX);

  const widthRef = useRef(document.body.clientWidth);

  useEffect(() => {
    window.addEventListener("resize", resizeWidth);
    return () => {
      window.removeEventListener("resize", resizeWidth);
    };
  }, []);

  return (
    <div className={styles.container}>
      {data.map((d, i) => {
        const { bgStyle, ...other } = d;
        const s = Object.assign({}, bgStyle, calcStyle(index, i));
        return <Page key={d.id} {...other} bgStyle={s} />;
      })}
      <Progress onChange={changeActivePage} />
    </div>
  );
}

export default Carousel;
