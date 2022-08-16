import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import CarouselItem from "./components/CarouselItem";
interface listItem {
  title?: string;
  describe?: string;
  className?: string;
}
export let list: listItem[] = [
  {
    title: "xPhone",
    describe: "Lots to love.Less to spend.\n Starting at $399",
    className: "item-xPhone",
  },
  {
    title: "Tablet",
    describe: "Just the right amount of everything.",
    className: "item-Tablet",
  },
  {
    title: "Buy a Tablet or xPhone for college.\n Get arPods.",
    className: "item-airpods",
  },
];
function App() {
  return (
    <div className="App" data-testid="test-app-id">
      <Carousel
        height="100vh"
        autoplay={true}
        duration={300}
        interval={3000}
        initialIndex={0}
      >
        {list.map((item) => {
          return (
            <CarouselItem
              key={item.title}
              className={`carousel-item ${item.className}`}
            >
              <div className="carousel-item-content">
                {!!item.title && (
                  <div className="carousel-item-title">{item.title}</div>
                )}
                {!!item.describe && (
                  <div className="carousel-item-describe">{item.describe}</div>
                )}
              </div>
            </CarouselItem>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
