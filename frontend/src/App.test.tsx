import React from 'react';
import { render } from '@testing-library/react';
import Swiper from './Slider/domain/Swiper';
import { useSwiperFn } from './Slider/application/useSwiperFn';
import Carousel from './Slider/ui'

test('test swiper domain', () => {
  const imgList = [
    {
      title: 'iphone',
      src: require('./assets/iphone.png'),
      alt: 'images-1',
    },
    {
      title: 'tablet',
      src: require('./assets/tablet.png'),
      alt: 'images-2',
    },
    {
      title: 'airpods',
      src: require('./assets/airpods.png'),
      alt: 'images-3',
    }
  ];
  const swiper = new Swiper({
    sliderList: imgList,
    autoPlay: true,
    dots: true,
    speed: 1000,
    delay: 3000,
  })
  // 测试swiper speed
  expect(swiper.getSpeed()).toBe(1000)
  // 测试swiper get index
  expect(swiper.getSliderIndex()).toBe(0)
  // 测试swiper get delay
  expect(swiper.getSliderDelay()).toBe(3000)
  // 测试swiper get sliderList
  expect(swiper.getSliderList().length).toBe(3)
  // 测试swiper append方法
  swiper.append({
    title: 'airpods',
    src: require('./assets/airpods.png'),
    alt: 'images-3',
  })
  expect(swiper.getSliderList().length).toBe(4)
});

test('test useSwiperFn', () => {
  const imgList = [
    {
      title: 'iphone',
      src: require('./assets/iphone.png'),
      alt: 'images-1',
    },
    {
      title: 'tablet',
      src: require('./assets/tablet.png'),
      alt: 'images-2',
    },
    {
      title: 'airpods',
      src: require('./assets/airpods.png'),
      alt: 'images-3',
    }
  ];
  const swiper = new Swiper({
    sliderList: imgList,
    autoPlay: true,
    dots: true,
    speed: 1000,
    delay: 3000,
  })
  // 测试swiper useSwiperFn
  const { moveSlider, getSliderIndex, setSliderIndex, getSliderList } = useSwiperFn(swiper)
  // 当前index=0；
  moveSlider(getSliderIndex());
  // move后index=1
  expect(getSliderIndex()).toEqual(1)
  // 设置index=2
  setSliderIndex(2)
  // 获取值index=2
  expect(getSliderIndex()).toEqual(2)
  // 获取Slider的数量
  expect(getSliderList().length).toEqual(3)
})

test('test dom', () => {
  // 由于轮播图采用数据驱动渲染，存在异步问题，测试dom会出现代码无法在浏览器中运行的情况
  // 暂无好的测试解决方案
  // 故没有测试dom
  const imgList = [
    {
      title: 'iphone',
      src: require('./assets/iphone.png'),
      alt: 'images-1',
    },
    {
      title: 'tablet',
      src: require('./assets/tablet.png'),
      alt: 'images-2',
    },
    {
      title: 'airpods',
      src: require('./assets/airpods.png'),
      alt: 'images-3',
    }
  ];

  const carousel = render(<Carousel
    sliderList={imgList}
    dots={true}
    speed={1000}
    delay={3000}
  ></Carousel>)
  // expect(carousel.findAllByRole('iphone')).toBe('iphone')
})