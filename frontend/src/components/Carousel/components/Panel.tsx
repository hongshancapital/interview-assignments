import React from 'react'
import { CarouseOption } from '../types'

interface Props {
  data: CarouseOption
}

function CarouselPanel(props: Props): React.ReactElement {
  const { title, subTitle, url, color } = props.data
  const style = { backgroundImage: `url(${url})`, color }

  return (
    <div className="carouse-panel" style={style}>
      {title && <h3 className="title">{title}</h3>}
      {subTitle && <h5 className="subtitle">{subTitle}</h5>}
    </div>
  )
}

export default CarouselPanel
