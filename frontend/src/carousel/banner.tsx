import React from "react";

interface IBanner{
  bgColor: string;
  fontColor: string;
  title: string;
  h2: string;
  imgUrl: any;
}
const Banner = (
  {bgColor, fontColor, title, h2, imgUrl}: IBanner
) => {
  return <div style={{background: bgColor, color: fontColor}}>
    <div className='banner'>
      <div className='textContent' >
        <h1>{title}</h1>
        <h2>{h2}</h2>
      </div>
      <div className='imgContent'>
        <img src={imgUrl} alt=""/>
      </div>
    </div>
  </div>
}

export default Banner;
