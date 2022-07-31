import data from "@/assets/data/data";
import { useEffect, useRef, useState } from "react";
import style from "./progress.module.sass";
import clsx from "clsx";
import { CAROUSEL_TIME, INDEX } from "@/constants/const";

interface ProgressProps {
  onChange: (index: number) => void;
}

function Progress(props: ProgressProps) {
  const length = data.length;
  const [index, setIndex] = useState(INDEX);
  const [init, setInit] = useState(false);
  const timerRef = useRef<NodeJS.Timer>();

  useEffect(() => {
    timerRef.current = setInterval(() => {
      setIndex((prev) => {
        const cur = prev + 1 >= length ? 0 : prev + 1;
        // props.onChange(cur);
        return cur;
      });
    }, CAROUSEL_TIME);
    setInit(true);
    return () => {
      timerRef.current && clearInterval(timerRef.current);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    props.onChange(index);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [index]);

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
