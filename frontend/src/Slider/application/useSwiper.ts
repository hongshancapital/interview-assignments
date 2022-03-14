import React, { useEffect, useMemo, useState } from "react"
import Swiper from "../domain/Swiper"
import { useSwiperFn } from "./useSwiperFn"

const useSwiper = (props: SwiperProps & { children?: React.ReactChildren }) => {
  // swiper 实例
  const swiper = useMemo(() => {
    const swiper = new Swiper({
      sliderList: props.sliderList || props.children,
      autoPlay: props.autoPlay,
      dots: props.dots,
      speed: props.speed,
      delay: props.delay,
    })
    return swiper;
  }, [])

  // 状态 sliderList
  const [sliderList, setSliderList] = useState<Array<SliderProps>>([])

  // 暂停
  const [sliderPause, setSliderPause] = useState(true)

  // 是否自动播放
  const [sliderAutoPlay, setSliderAutoPlay] = useState<boolean | undefined>(true)

  // 是否显示 dots
  const [sliderDots, setSliderDots] = useState<boolean | undefined>(true)

  // sliderList 总数
  const [sliderCount, setSliderCount] = useState(0)

  // slider 滑动时间
  const [sliderSpeed, setSliderSpeed] = useState<number | undefined>(3000)

  // slider dot 滑动时间
  const [sliderDelay, setSliderDelay] = useState<number | undefined>(3000)

  // slider 索引
  const [index, setIndex] = useState<number | undefined>()

  // swiper 依赖函数
  const {
    onPlay,
    onPause,
    getSliderList,
    getSliderCount,
    getSliderIndex,
    setSliderIndex,
    moveSlider,
  } = useSwiperFn(swiper)

  // 播放暂停功能
  const playChanged = (data: boolean) => {
    setSliderPause(data)
  }

  // 设置展示的数据
  const sliderChanged = (data: Array<SliderProps>) => {
    setSliderList(data)
  }

  // 索引变动
  const indexChanged = (index: number) => {
    setIndex(index)
  }

  // swiper init finished
  const onFinsh = () => {
    setSliderList(swiper.getSliderList())
    setSliderAutoPlay(swiper.isAutoPlay())
    setSliderDots(swiper.getSliderDots())
    setSliderCount(swiper.getSliderCount())
    setSliderSpeed(swiper.getSpeed())
    setSliderDelay(swiper.getSliderDelay())
    setIndex(swiper.getSliderIndex())
  }

  // 设置swiper 和 slider的初始化样式
  useEffect(() => {
    const swiperStyle = {
      width: 100 * swiper.getSliderCount() + "%",
      left: 0 + "%",
      transitionDuration: (swiper.getSpeed() as number) / 1000 + "s",
    }
    const sliderStyle = {
      width: 100 / swiper.getSliderCount() + '%'
    }
  }, [sliderCount])

  // 初始化监听数据
  useEffect(() => {
    swiper.addListener('pause', playChanged)
    swiper.addListener('sliderChanged', sliderChanged)
    swiper.addListener('indexChanged', indexChanged)
    swiper.addListener('finished', onFinsh)
    swiper.initFinish()
    let sliderInterval: any;
    if (swiper.isAutoPlay()) {
      sliderInterval = setInterval(() => {
        moveSlider(swiper.getSliderIndex())
      }, swiper.getSliderDelay())
    }
    return () => {
      swiper.removeListener('pause', playChanged)
      swiper.removeListener('sliderChanged', sliderChanged)
      swiper.removeListener('indexChanged', indexChanged)
      sliderInterval && clearInterval(sliderInterval)
    }
  }, [])

  return {
    swiper,
    sliderPause,
    sliderList,
    sliderAutoPlay,
    sliderCount,
    sliderDots,
    sliderSpeed,
    sliderDelay,
    index,
    // swiperStyle,
    // sliderStyle,
    onPlay,
    onPause,
    getSliderList,
    getSliderCount,
    getSliderIndex,
    setSliderIndex,
    moveSlider,
  };
}

export { useSwiper }