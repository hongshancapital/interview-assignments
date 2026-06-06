/**
 * @description 全屏轮播
 */
import './index.css'
import CoreCarousel from '../../../../components/carousel/core'

export interface CarouselItemFace {
  title?: string;
  desc?: string;
  bgImg?: string;
  bgColor?: string;
  fontColor?: string;
}
interface CarouselFullScreenFace {
  carouselList?: CarouselItemFace[]
}

function CarouselItem(data: CarouselItemFace) {
  const {
    title,
    desc,
    bgImg,
    bgColor = '#000',
    fontColor = '#fff'
  } = data;

  return (
    <div
      className="carousel-item-box"
      style={{color: fontColor, backgroundColor: bgColor, backgroundImage: `url(${bgImg})`}}>
      <div className="carousel-item-info">
        <p className="carousel-item-title">{ title }</p>
        <p className="carousel-item-desc">{ desc }</p>
      </div>
    </div>
  )
}

export default function CarouselFullScreen({carouselList = []}: CarouselFullScreenFace) {
  return (
    <CoreCarousel duration={3000}>
      {
        carouselList.map((item, index) => <CarouselItem {...item} key={index} />)
      }
    </CoreCarousel>
  )
}