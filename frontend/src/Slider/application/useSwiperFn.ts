import Swiper from "../domain/Swiper"
/*
 * swiper 依赖函数
 */
const useSwiperFn = (swiper: Swiper) => {

  const onPlay = () => {
    swiper.onPlay()
  }

  const onPause = () => {
    swiper.onPause()
  }

  const getSliderList = () => {
    return swiper.getSliderList()
  }

  const getSliderCount = () => {
    return swiper.getSliderCount()
  }

  const getSliderIndex = () => {
    return swiper.getSliderIndex()
  }

  const setSliderIndex = (value: number) => {
    return swiper.setSliderIndex(value)
  }

  const moveSlider = (index: number) => {
    let current = index;
    if (current + 1 === getSliderCount()) {
      current = 0;
    } else {
      current = current + 1;
    }
    setSliderIndex(current)
  }

  return {
    onPlay,
    onPause,
    getSliderList,
    getSliderCount,
    getSliderIndex,
    setSliderIndex,
    moveSlider
  }
}

export { useSwiperFn }