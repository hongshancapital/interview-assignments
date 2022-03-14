import React from "react";

const SliderItemComponent = (
  props: { sliderItem: SliderProps, style?: React.CSSProperties }
) => {
  const { sliderItem } = props;
  return (
    <div className='slider'
      style={props.style}
    >
      {
        React.isValidElement(sliderItem) ?
          sliderItem
          :
          <>
            <div className='title'>{sliderItem.title}</div>
            <img src={sliderItem.src} />
          </>
      }
    </div>
  )
}

export default SliderItemComponent;