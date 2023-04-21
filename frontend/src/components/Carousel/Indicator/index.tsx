import classNames from "classnames";
import { useMemo } from "react";
import classes from "./index.module.scss";

interface IndicatorProps {
  active?: boolean;
  duration: number;
}

const Indicator = (props: IndicatorProps) => {
  const { active, duration } = props;

  const cls = classNames({
    [classes.indicator]: true,
    [classes["indicator-active"]]: active,
  });

  const progressStyle = useMemo(
    () => ({
      transition: `width ${duration / 1000}s ease-out`,
    }),
    [duration]
  );

  return (
    <span className={cls}>
      <span
        className={classes["indicator-progress"]}
        style={progressStyle}
      ></span>
    </span>
  );
};

export default Indicator;
