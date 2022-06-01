import React from "react";
import "./App.scss";
import { Carousel, CarouselItem } from "./components/carousel";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

const App = () => {
  const carouselList = [
    {
      className: "iphone-slide",
      titles: ["xPhone"],
      subTitles: ["Lots ot love.Less to spend."],
      imgSrc: iphoneImg,
      alt: "iphone"
    },
    {
      className: "tablet-slide",
      titles: ["Tablet"],
      subTitles: ["Just the right amount of everything."],
      imgSrc: tabletImg,
      alt: "iphone"
    },
    {
      className: "airpods-slide",
      titles: ["Buy a Tablet or xPhone by a College.", "Get arPods."],
      imgSrc: airpodsImg,
      alt: "airpods"
    }
  ]
  return (
    <div className="App">
      <Carousel>
        {carouselList.map(({ className, titles, subTitles, imgSrc, alt }) => (
          <CarouselItem className={className} key={className}>
            <div className="slide-container">
              {
                titles.map((title) => <div className="title" key={title} >{title}</div>)
              }
              {
                subTitles?.map((subTitle, subTitleIndex) => <div className="sub-title" key={subTitleIndex}>{subTitle}</div>)
              }
              <div className="img-container">
                <img src={imgSrc} alt={alt} />
              </div>
            </div>
          </CarouselItem>
        ))}
      </Carousel>
    </div>);
}

export default App;
