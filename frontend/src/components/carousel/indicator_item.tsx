import React, { useState, useRef, useEffect } from "react";

const IndicatorItem = (props: any) => {
  const [percent, setPercent] = useState<number>(0);
  const [step] = useState<number>(props.duration / 10 || 10);
  let itemRef = useRef<any>(null);
  itemRef.current = () => works();
  const works = (): void => {
    setPercent(percent + 10);
  };

  useEffect(() => {
    if (props.idx !== props.mkey) setPercent(0);
    let id = setInterval(itemRef.current, step);
    return () => clearInterval(id);
  }, [percent]);
  return (
    <div
      className={`_indicator_item ${props.idx === props.mkey ? "actived" : ""}`}
      onClick={()=>props.click(props.mkey)}
    >
      <span
        style={{
          width: `${percent}%`,
        }}
      ></span>
    </div>
  );
};

export default IndicatorItem;
