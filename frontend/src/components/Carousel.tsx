import React, { ReactElement } from "react";

export interface Props {
  duration?: number // ms
  data: any[]
}

function Carousel(props: Props): ReactElement {
  let { data, duration } = props;

  return (
    data.length ?
      data.map((item, index) => (
        <div className="carousel" key={index}>
          <h1 className="title">{item.title}</h1>
          <p className="description">{item.description}</p>
          <p className="price">{item.price}</p>
        </div>
      ))
      :
      <div>nodata</div>
  )
}

export default Carousel;