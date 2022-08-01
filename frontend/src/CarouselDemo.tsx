import React from "react";
import Carousel, { CarouselProps } from "./components/Carousel/Carousel";
import airpodsImg from "./assets/airpods.png";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
export const list = [
  {
    title: "xPhone",
    descs: ["Lots to love, Less to spend.", "Starting at $399"],
    bgImage: iphoneImg,
  },
  {
    title: "Tablet",
    descs: ["Just the right amount of everything"],
    bgImage: tabletImg,
  },
  {
    title: "Buy a Tablet or xPhone for college Get arPods",
    bgImage: airpodsImg,
  },
];

export const carouselDemoProps = {
  width: 800,
  height: 450,
  className: "carousel-demo",
  speed: 100,
  delay: 2000,
};
export const CarouselDemo = (
  testProps?: Omit<CarouselProps, keyof typeof carouselDemoProps>
) => {
  return (
    <Carousel {...carouselDemoProps} {...testProps}>
      {list.map((item, index) => (
        <div className="carousel-demo-bg" key={index} style={{ backgroundImage: `url(${item.bgImage})` }}>
          <h1>{item.title}</h1>
          {item?.descs?.length ? item.descs.map((desc, i) => <p key={i}>{desc}</p>) : null}
        </div>
      ))}
    </Carousel>
  );
};
