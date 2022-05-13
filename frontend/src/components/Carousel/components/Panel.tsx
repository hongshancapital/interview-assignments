import React from 'react'
import { Options } from '../Options'

interface Props {
  data: Options
}

function CarouselPanel(props: Props): React.ReactElement {
  const { data } = props
  const { title, subTitle, url, color } = data
  const style = { backgroundImage: `url(${url})`, color }

  return (
    <div className="carouse-panel" style={style}>
      {title && <h3 className="title">{title}</h3>}
      {subTitle && <h5 className="subtitle">{subTitle}</h5>}
    </div>
  )
}

export default React.memo(CarouselPanel)
