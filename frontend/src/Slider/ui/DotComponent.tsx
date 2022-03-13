import { CSSProperties } from "react";

const DotItemComponent = (props: {
  currentIndex: number,
  sliderIndex: number | undefined,
  delay: number | undefined
}) => {
  const { sliderIndex, currentIndex, delay } = props;
  const style: CSSProperties = {
    width: '100px',
    transitionDuration: `${(delay as number) / 1000}s`
  }
  return (
    <div className={`dot-item`}>
      <div
        className={`dot-item-inner`}
        style={sliderIndex === currentIndex ? style : {}}
      ></div>
    </div>
  )
}

export default DotItemComponent;