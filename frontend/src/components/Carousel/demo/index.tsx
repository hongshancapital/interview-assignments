import React, { useState, useEffect } from "react";
import Carousel from "..";
import '../../Carousel/style'
import './index.scss';
import airport from '../../../assets/airpods.png'
import iphone from '../../../assets/iphone.png'
import tablet from '../../../assets/tablet.png'

type dataType = {
  imgUrl: string;
  clickUrl: string;
}[]

const Demo = () => {
  let [data, setData] = useState<dataType>([])

  useEffect(() => {
    setTimeout(() => {
      setData(
        [{ imgUrl: airport, clickUrl: "/" },
        { imgUrl: iphone, clickUrl: "/" },
        { imgUrl: tablet, clickUrl: "/" }]
      )
    }, 100)

  }, [])


  return <div className="carousel-container">
    <Carousel data={data} panigationProps={
      {
        style: { bottom: "50px" },
        etiming: 3000,
      }} />
  </div>
}


export default Demo