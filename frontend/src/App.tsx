import React from "react";
import Carousel from "./components/carousel/Carousel";
import CarouselItem from "./components/carouselItem/CarouselItem";
import BannerItem, { BannerProps } from "./components/bannerItem/BannerItem";
import airpods from "./assets/airpods.png";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";

const bannerList: BannerProps[] = [
  {
    title: "xPhone",
    subTitle: "L ots to love. L ess to spend.Starting at $399.",
    img: iphone,
    color: "#fff",
  },
  {
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    img: tablet,
    color: "#000",
  },
  {
    title: "Buy a Tablet or xPhone for college.Get arPods.",
    img: airpods,
    color: "#000",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel>
        {bannerList.map((item) => (
          <CarouselItem key={item.title}>
            <BannerItem {...item}></BannerItem>
          </CarouselItem>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
