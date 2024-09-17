import "./App.css";
import { Carousel } from "./components/Carousel";
import { CarouselItem } from "./components/CarouselItem";

import iphoneImage from "./assets/iphone.png";
import tabletImage from "./assets/tablet.png";
import airPodsImage from "./assets/airpods.png";

interface CarouselItemOptions {
  id: string;
  img: string;
  title: string;
  text: string;
  mode?: "light" | "dark";
}

function App() {
  const carouselItems: CarouselItemOptions[] = [
    {
      id: "iphone",
      img: iphoneImage,
      title: "xPhone",
      text: "Lots to love. Less to spend. Starting at $399.",
      mode: "dark",
    },
    {
      id: "tablet",
      img: tabletImage,
      title: "Tablet",
      text: "Just the right amount of everything",
    },
    {
      id: "airpods",
      img: airPodsImage,
      title: "Buy a Tablet or xPhone for college. Get airPods.",
      text: "",
    },
  ];
  return (
    <div className="App">
      <Carousel height={"500px"} width={"800px"}>
        {carouselItems.map((item) => (
          <CarouselItem key={item.id} mode={item.mode ?? "light"}>
            {/* use props.children to make CarouselItem more customizable, 
            you can put whatever you want here wrapped by CarouselItem.
            And render it with your custom classes */}

            {/* title */}
            <div className="title">{item.title}</div>
            {/* text content */}
            <div className="text">{item.text}</div>
            {/* background image */}
            <div
              className="img"
              style={{
                backgroundImage: `url(${item.img})`,
              }}
            ></div>
          </CarouselItem>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
