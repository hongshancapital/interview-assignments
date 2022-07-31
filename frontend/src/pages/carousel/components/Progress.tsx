import { useEffect, useRef, useState } from "react";
import style from "./progress.module.sass";
import clsx from "clsx";
import { INDEX } from "@/constants/const";
import { DataItem } from "@/assets/data/data";

interface ProgressProps {
  datasource: DataItem[];
  interval: number;
  onChange: (index: number) => void;
}

function Progress({ datasource, interval, onChange }: ProgressProps) {
  const [index, setIndex] = useState(INDEX);
  const [init, setInit] = useState(false);
  const timerRef = useRef<NodeJS.Timer>();

  useEffect(() => {
    timerRef.current = setInterval(
      () => setIndex((prev) => (prev + 1 >= datasource.length ? 0 : prev + 1)),
      interval
    );
    setInit(true);
    setIndex(INDEX);
    return () => {
      timerRef.current && clearInterval(timerRef.current);
    };
  }, [interval, datasource.length]);

  useEffect(() => {
    onChange(index);
  }, [index, onChange]);

  return (
    <div className={style.progressContainer}>
      {datasource.map((d, i) => (
        <div className={style.progressItem} key={d.id}>
          <div
            className={clsx(style.progressBg, { [style.active]: i === index })}
            style={{
              width: !init ? "0px" : index === i ? "100%" : "0px",
              transition:
                i === index ? `width ${interval / 1000}s linear` : "none",
            }}
          ></div>
        </div>
      ))}
    </div>
  );
}

export default Progress;
