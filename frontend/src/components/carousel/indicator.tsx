import React, { useState } from "react";
import IndicatorItem from "./indicator_item";
import { numberArray } from "./tool";

const Indicator = (props: any) => {
  const [num] = useState<Array<number>>(numberArray(props.num));
  return (
    <div className="_indicator">
      {num.map((i: number) => {
        return (
          <IndicatorItem
            key={i}
            mkey={i}
            idx={props.idx}
            duration={props.duration}
            click={props.click}
          />
        );
      })}
    </div>
  );
};

export default Indicator;
