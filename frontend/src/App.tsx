import Carousel, { CarouselItem } from "./components/Carousel";
import { IImageItem } from "./components/Carousel/types";
import Airpods from "./assets/airpods.png";
import IPhone from "./assets/iphone.png";
import Tablet from "./assets/tablet.png";
import "./App.css";

const imageList: IImageItem[] = [
  {
    title: "xPhone",
    description: "Lots to love. Less to spend \nStarting at $399.",
    img: IPhone,
    textColor: "#FFFFFF",
  },
  {
    title: "Tablet",
    description: "Just the right amount of everything.",
    img: Tablet,
  },
  {
    title: "Buy a Tablet or xPhone for college.\nGet airPods.",
    img: Airpods,
  },
];

function App() {
  return (
    <div className="App">
      <Carousel boxStyle={{ height: "50vh" }}>
        {imageList.map((item) => (
          <CarouselItem key={item.img} data={item}></CarouselItem>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
