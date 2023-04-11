import { useEffect, useState } from "react";

import imgIphone from "../assets/iphone.png";
import imgTablet from "../assets/tablet.png";
import imgAirpods from "../assets/airpods.png";

import { CarouselItemContentProps } from "../components/carousel/carousel-item-content";

const STATIC_DATA: CarouselItemContentProps[] = [
  {
    imgUrl: imgIphone,
    imgSize: 300,
    title: "xPhone",
    subTitle: "Lots to love.Less to spend.\nStarting at $399.",
    style: { color: "#FFF", backgroundColor: "rgb(17,17,17)" },
  },
  {
    imgUrl: imgTablet,
    imgSize: 300,
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    style: { color: "#000", backgroundColor: "rgb(250,250,250)" },
  },
  {
    imgUrl: imgAirpods,
    imgSize: 300,
    title: "Buy a Tablet or xPhone for college.\nGet airPods",
    style: { color: "#000", backgroundColor: "rgb(241,241,243)" },
  },
];

const useCarouselData = () => {
  const [data, setData] = useState<Array<CarouselItemContentProps>>([]);

  useEffect(() => {
    setData(STATIC_DATA);
  }, []);

  return {
    data,
  };
};

export default useCarouselData;
