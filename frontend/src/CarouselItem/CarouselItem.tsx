import React from "react"
import './CarouselItem.scss';

export interface Props {
  img: string;
  title: string[];
  subTitle: string[];
  textColor: string;
}

export const CarouselItem: React.FC<Props> = (props) => {
  const { img, title, subTitle, textColor } = props

  return <div className="carouselitem" style={{ backgroundImage: `url(${img})`, color: textColor }}>
    {
      title.map((text) => (<h1 key={text}>{text}</h1>))
    }
    {
      subTitle.map((text) => (<h5 key={text}>{text}</h5>))
    }
  </div>
}