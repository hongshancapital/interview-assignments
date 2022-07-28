import data from "@/assets/data/data";
import style from "./progress.module.sass";

interface ProgressProps {
  active: number;
}

function Progress(props: ProgressProps) {
  return (
    <div className={style.progressContainer}>
      {data.map((d, i) => (
        <div className={style.progressItem} key={d.id}>
          <div
            className={style.progressBg}
            style={{ width: props.active === i ? "100%" : "0px" }}
          ></div>
        </div>
      ))}
    </div>
  );
}

export default Progress;
