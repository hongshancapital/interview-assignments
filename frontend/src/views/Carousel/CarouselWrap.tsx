import React, { useMemo } from 'react';
import useIndex from '../../hooks/useIndex';
import './CarouselWrap.css'
import Carousel from './Carousel';

export interface ImageItem {
  src:string,
  title: string
  describe?: string
}

const CarouselWrap = () => {
  const imageList: ImageItem[] = [
    {src: require('../../assets/iphone.png'), title: 'xPhone', describe: 'Lots to love. Less to spend.Starting at $399.'},
    {src: require('../../assets/tablet.png'), title: 'Tablet', describe: 'Just the right amount of everything.'},
    {src: require('../../assets/airpods.png'), title: 'Buy a Tablet or xPhone for college. Get arPods.'},
  ]

  const curIndex = useIndex(imageList.length)
  const positionLeft = useMemo(() => `${-curIndex * 100}vw`, [curIndex])

  return <div className='carousel-wrap'>
    <Carousel left={positionLeft} imageList={imageList} curIndex={curIndex}/>
  </div>
}

export default CarouselWrap