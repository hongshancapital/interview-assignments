import React from "react"
import './CarouselItem.scss';

export interface Props {
  id: number;
  img: string;
  title: string[];
  subTitle: string[];
  textColor: string;
  bgColor: string;
}

export const CarouselItem: React.FC<Props> = (props) => {
  const { img, title, subTitle, textColor, id, bgColor } = props

  return <div className="carouselitem" style={{ color: textColor }}>
    {/* <img src={img} alt="img" /> */}
    <div className="carouselitem-context" style={{ backgroundSize: `${0.5 * id * 100}%`, backgroundImage: `url(${img})`, backgroundColor: bgColor }}>
      {
        title.map((text) => (<h1 key={text}>{text}</h1>))
      }
      {
        subTitle.map((text) => (<h5 key={text}>{text}</h5>))
      }
    </div>
  </div>
}