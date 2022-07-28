import data from "@/assets/data/data";
import { useEffect, useRef, useState } from "react";
import style from "./progress.module.sass";
import clsx from "clsx";

interface ProgressProps {
  onChange: (index: number) => void;
}

// 轮播时间
const CAROUSEL_TIME = 5000;

function Progress(props: ProgressProps) {
  const length = data.length;
  const [index, setIndex] = useState(1);
  const [init, setInit] = useState(false);
  const timerRef = useRef<NodeJS.Timer>();

  useEffect(() => {
    timerRef.current = setInterval(() => {
      setIndex((prev) => {
        const cur = prev + 1 >= length ? 0 : prev + 1;
        props.onChange(cur);
        return cur;
      });
    }, CAROUSEL_TIME);
    setInit(true);
    return () => {
      timerRef.current && clearInterval(timerRef.current);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className={style.progressContainer}>
      {data.map((d, i) => (
        <div className={style.progressItem} key={d.id}>
          <div
            className={clsx(style.progressBg, { [style.active]: i === index })}
            style={{ width: !init ? "0px" : index === i ? "100%" : "0px" }}
          ></div>
        </div>
      ))}
    </div>
  );
}

export default Progress;
