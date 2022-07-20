import React from "react";

const SliderItemComponent = (
  props: { sliderItem: SliderProps, style?: React.CSSProperties }
) => {
  const { sliderItem, style } = props;
  const sliderItemStyle = !React.isValidElement(sliderItem) ? sliderItem.style : null
  const mergeStyle = { ...sliderItemStyle, ...style, }
  return (
    <div className='slider'
      style={mergeStyle}
    >
      {
        React.isValidElement(sliderItem) ?
          sliderItem
          :
          <>
            <div className='description-info'>
              <div className='title'>
                {
                  sliderItem.descriptionInfo.title.map((title: string, index: number) => {
                    return <p key={index}>{title}</p>
                  })
                }
              </div>
              <div className='description'>
                {
                  sliderItem.descriptionInfo.description.map((description, index) => {
                    return <p key={index}>{description}</p>
                  })
                }
              </div>
            </div>
            <div className='image-container'>
              <img src={sliderItem.imageInfo.src} alt={sliderItem.imageInfo.alt} />
            </div>
          </>
      }
    </div>
  )
}

export default SliderItemComponent;