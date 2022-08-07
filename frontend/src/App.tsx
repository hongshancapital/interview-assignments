import React from "react";
import "./App.css";
import { Carousel, CarouselItem } from "./components/Carousel";
import HeroView from "./components/HeroView";
import AirpodsImageURL from "./assets/airpods.png";
import iPhoneImageURL from "./assets/iphone.png";
import TabletImageURL from "./assets/tablet.png";

interface HeroViewConfigItem {
  title: string;
  subtitle?: string;
  image: string;
  theme: "light" | "dark";
}
const HeroViewConfig: HeroViewConfigItem[] = [
  {
    title: "xPhone",
    subtitle: "Lots to love. Less to spend.\nStarting at $399.",
    image: iPhoneImageURL,
    theme: "dark",
  },
  {
    title: "Tablet",
    subtitle: "Just the right amount of everything.",
    image: TabletImageURL,
    theme: "light",
  },
  {
    title: "Buy a Tablet or xPhone for college.\nGet arPods.",
    image: AirpodsImageURL,
    theme: "light",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel themeList={HeroViewConfig.map(item => item.theme)}>
        {HeroViewConfig.map(item => (
          <CarouselItem key={item.title}>
            <HeroView
              theme={item.theme}
              title={item.title}
              subtitle={item.subtitle}
              backgroundImageURL={item.image}
            />
          </CarouselItem>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
