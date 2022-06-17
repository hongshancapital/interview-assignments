import React from 'react'
import './RotationItem.scss'

interface Props {
  data: RotationItem
}

function RotiationItem(props: Props) {
  const { data } = props
  const { title, subTitle, image, theme = 'light', color = '' } = data
  return (
    <div
      className={`rotation-item ${theme}`}
      style={{
        backgroundImage: `${image ? `url(${image})` : 'none'}`,
        backgroundColor: `${color || 'inhert'}`,
      }}
    >
      {title && (
        <h1 className="title" dangerouslySetInnerHTML={{ __html: title }}></h1>
      )}
      {subTitle && (
        <h2
          className="sub-title"
          dangerouslySetInnerHTML={{ __html: subTitle }}
        ></h2>
      )}
    </div>
  )
}

export default RotiationItem
