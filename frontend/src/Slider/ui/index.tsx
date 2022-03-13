import DotComponent from './DotComponent';
import SliderItemComponent from './SliderItemComponent';
import { useSwiper } from '../application/useSwiper';
import './index.css'

const Swiper = (props: SwiperProps) => {
  const {
    swiper,
    sliderList,
    sliderDots,
    sliderDelay,
    index,
  } = useSwiper(props)

  const _index = index as number;
  const swiperStyle = {
    width: 100 * swiper.getSliderCount() + "%",
    left: -100 * (_index <= 0 ? 0 : _index) + "%",
    transitionDuration: (swiper.getSpeed() as number) / 1000 + "s",
  }

  const sliderStyle = {
    width: 100 / swiper.getSliderCount() + '%'
  }

  // 渲染Slider
  const renderSlider = () => {
    return sliderList.length > 0 ?
      [...sliderList].map((item: SliderProps, inx: number) => {
        return <SliderItemComponent
          key={inx}
          sliderItem={item}
          style={sliderStyle} />
      }) : null
  }

  // 渲染dot
  const renderDots = () => {
    return sliderDots ?
      <div className="dot-list">
        {
          [...sliderList].map((_, inx: number) => {
            return <DotComponent
              key={inx}
              currentIndex={inx}
              delay={sliderDelay}
              sliderIndex={index} />
          })}
      </div> : null
  }

  return (
    <div className="swiper">
      <div className="slider-list" style={swiperStyle}>
        {renderSlider()}
      </div>
      {renderDots()}
    </div>
  )
}

export default Swiper