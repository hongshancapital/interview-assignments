import React, { useState } from "react";
import "./App.css";
import Swiper from "./components/swiper";
import SwiperItem from "./components/swiper-item"
import airpods from "./assets/airpods.png"
import iphone from "./assets/iphone.png"
import tablet from "./assets/tablet.png"

function App() {

  const images = [{
    path: iphone,
    title: "xPhone",
    detail: "Lost to Love.Less to spend.",
    price: "Strating at $399.",
    style: {
      backgroundColor: "rgb(17,17,17)",
      color: "white"
    }
  },
  {
    path: tablet,
    title: "Tablet",
    detail: "Just the right amount of everything.",
    style: {
      backgroundColor: "rgb(250,250,250)",

    },
    imgStyle: {
      width: "800px"
    }
  },
  {
    title: "Buy a Tablet or xPhone for college Get ArPods.",
    path: airpods,
    style: {
      backgroundColor: "rgb(241,241,243)",

    },
    imgStyle: {
      width: "1000px"
    }
  },

  ]

  return <div className="App">
    <Swiper isautoplay={true}>
      {images.map((el: any, index: number) => {
        return <SwiperItem key={index}>
          <div className="item-box" style={el.style}>
            <div className="item-center">
              <div className="item-title">{el.title}</div>
              <div className="item-detail">{el.detail}</div>
              <div className="item-price">{el.price}</div>
              <img style={el?.imgStyle} className="img-item" src={el.path} alt="" />
            </div>
          </div>
        </SwiperItem>
      })
      }
    </Swiper>
  </div>;
}

export default App;
