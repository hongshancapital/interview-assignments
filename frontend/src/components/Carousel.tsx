import React, { useState } from "react";

import airpods from "../assets/airpods.jpg";
import iphone from "../assets/iphone.jpg";
import tablet from "../assets/tablet.jpg";
import Slider, { Settings } from "react-slick";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./Carousel.css";

const progressOut = { width: 0, opacity: 0 };
const progressIn = { width: 50, opacity: 1 };

export interface ItemProps {
  src: any;
  titles: string[];
  subTitles?: string[];
  st?: React.CSSProperties;
}

// page data
const items: ItemProps[] = [
  {
    src: iphone,
    titles: ["xPhone"],
    subTitles: ["Lots to love. Less to spend.", "Starting at $399 "],
    st: { backgroundColor: "#111111" },
  },
  {
    src: tablet,
    titles: ["Tablet"],
    subTitles: ["Just the right amount of everything."],
    st: { backgroundColor: "#fafafa", color: "black" },
  },
  {
    src: airpods,
    titles: ["Buy a Tablet or xPhone for college. ", "Get airPods"],
    st: { backgroundColor: "#f1f1f3", color: "black" },
  },
];

function Carousel(setProps: Settings) {
  const [current, setCurrent] = useState(0);

  const innerSettings = {
    customPaging: function (i: any) {
      return (
        <div>
          <span style={i === current ? progressIn : progressOut}></span>
        </div>
      );
    },
    dots: true,
    infinite: true,
    autoplaySpeed: 3000,
    autoplay: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    pauseOnHover: false,
    beforeChange: (i: any, d: any) => {
      setCurrent(i);
    },
    dotsClass: "slick-dots",
    ...setProps,
  };

  const children =
    setProps.children ||
    items.map((it, i) => <SliderItem key={`slide_item_${i}`} {...it} />);

  return <Slider {...innerSettings}>{children}</Slider>;
}

export function SliderItem(prop: ItemProps, key?: string) {
  return (
    <div key={key} className="slider-item" style={{ ...prop.st }}>
      <div>
        {prop.titles.map((ti, i) => (
          <h1 key={`ti_${i}`}>{ti}</h1>
        ))}
        {prop.subTitles?.map((st) => (
          <h2>{st} </h2>
        ))}
      </div>
      {prop.src && <img src={prop.src} alt="" />}
    </div>
  );
}

export default Carousel;
