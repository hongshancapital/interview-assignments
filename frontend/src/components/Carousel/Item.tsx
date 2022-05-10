import React from "react";

interface Props {
  color: string
  texts?: string[]
  imgSrc: string
  titles?: string[]
  imgWidth: string
  background: string,
}

export function Item(props: Props) {
  const {background, color, titles = [], texts = [], imgSrc, imgWidth } = props

  return (
    <div className="banner" style={{ background, color }}>
      <div className="content">
        { titles.map(title => <div className="title">{ title }</div>) }
        { texts.map(text => <div className="text">{ text }</div>) }
      </div>
      <div className="banner-img-wrapper">
        <img src={imgSrc} alt="" style={{ width: imgWidth }}/>
      </div>
    </div>
  )
}
