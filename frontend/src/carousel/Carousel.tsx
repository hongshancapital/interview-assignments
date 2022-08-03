import Page from "./components/Page";
import Progress from "./components/Progress";
import { DataItem } from "../assets/data";
import { CAROUSEL_TIME, INDEX } from "../constants/const";
import styles from "./carousel.module.sass";
import { useState } from "react";

export interface CarouselProps {
  datasource: DataItem[];
  width: number;
  interval?: number;
}

function Carousel({
  datasource,
  width,
  interval = CAROUSEL_TIME,
}: CarouselProps) {
  function calcStyle(i: number, index: number) {
    return {
      transform: `translate(${(index - i) * width}px, 0px)`,
    };
  }

  function changeActivePage(index: number) {
    setIndex(index);
  }

  const [index, setIndex] = useState(INDEX);

  return (
    <div className={styles.container}>
      {datasource.map((d, i) => {
        const { bgStyle, ...other } = d;
        const s = Object.assign({}, bgStyle, calcStyle(index, i));
        return <Page key={d.id} {...other} bgStyle={s} />;
      })}
      <Progress
        datasource={datasource}
        interval={interval}
        onChange={changeActivePage}
      />
    </div>
  );
}

export default Carousel;
