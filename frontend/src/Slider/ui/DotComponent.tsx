import React from "react";
import { CSSProperties } from "react";

const DotItemComponent = (props: {
  currentIndex: number,
  sliderIndex: number | undefined,
  delay: number | undefined
}) => {
  const { sliderIndex, currentIndex, delay } = props;
  const style: CSSProperties = {
    animationDuration: `${(delay as number) / 1000}s`
  }
  return (
    <div className={`dot-item`}>
      <div
        className={`${sliderIndex === currentIndex ? 'dot-item-inner' : ''}`}
        style={sliderIndex === currentIndex ? style : {}}
      ></div>
    </div>
  )
}

export default DotItemComponent;