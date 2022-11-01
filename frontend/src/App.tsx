import React from "react";
import "./App.css";
import AirPodsImg from "./assets/airpods.png";
import IPhoneImg from "./assets/iphone.png";
import TabletImg from "./assets/tablet.png";
import CarouselProvider, { Carousel, Paper } from "./components/Carousel";
import Navigation from "./components/Navigation";

const carouselData = [
  {
    className: "x-phone",
    img: IPhoneImg,
    title: "xPhone",
    desc: `Lots to love. Less to spend.
Starting at $399.`,
  },
  {
    className: "tablet",
    img: TabletImg,
    title: "Tablet",
    desc: "Just the right amount of everything.",
  },
  {
    className: "ar-pods",
    img: AirPodsImg,
    title: `Buy a Tablet or xPhone for college.
Get arPods.`,
  },
];

function App() {
  return (
    <CarouselProvider speed={3000} transitionDuration={600} auto cycle>
      <div className="container">
        <Carousel>
          {carouselData.map((item) => {
            return (
              <Paper key={item.title} className={item.className}>
                <div className="info">
                  <h1>{item.title}</h1>
                  {item.desc && <p>{item.desc}</p>}
                </div>
                <img src={item.img} alt={item.desc || item.title} />
              </Paper>
            );
          })}
        </Carousel>

        <Navigation />
      </div>
    </CarouselProvider>
  );
}

export default App;
