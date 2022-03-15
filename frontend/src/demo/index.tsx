import React, { FC } from "react";
import Carousel from "./Carousel";
import picSrc1 from "../assets/airpods.png";
import picSrc2 from "../assets/iphone.png";
import picSrc3 from "../assets/tablet.png";
import "./style.css";

const Demo: FC = () => {
  const pictureArr = [
    {
      id: 1001,
      src: picSrc1,
    },
    {
      id: 1002,
      src: picSrc2,
    },
    {
      id: 1003,
      src: picSrc3,
    },
  ];

  return (
    <Carousel
      containerRect={{ height: "100vh", width: "100vw" }}
      pictureArr={pictureArr}
    />
  );
};

export default Demo;
