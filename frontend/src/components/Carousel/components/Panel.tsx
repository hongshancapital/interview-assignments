import React from 'react'
import { Options } from '../Options'

interface Props {
  data: Options,
  width?: number | string
}

function CarouselPanel(props: Props): React.ReactElement {
  const { data, width } = props
  const { title, subTitle, url, color } = data
  const style = { backgroundImage: `url(${url})`, color, width }

  return (
    <div className="carouse-panel" style={style}>
      {title && <h3 className="title">{title}</h3>}
      {subTitle && <h5 className="subtitle">{subTitle}</h5>}
    </div>
  )
}

export default React.memo(CarouselPanel)
