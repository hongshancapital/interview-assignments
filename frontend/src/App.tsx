import { Carousel } from "components/Carousel";

import IphoneImg from "assets/iphone.png";
import TabletImg from "assets/tablet.png";
import AirpodsImg from "assets/airpods.png";
import type { CarouselItemProps } from "components/Carousel";
import "./App.css";

export const items: CarouselItemProps[] = [
  {
    id: 1,
    img: IphoneImg,
    title: "xPhone",
    desc: "Lots to Love. Less to spend. \n Starting at $399.",
    color: "#fff",
    bgColor: "#111",
  },
  {
    id: 2,
    img: TabletImg,
    title: "Tablet",
    desc: "Just the right amount of everything.",
    color: "#000",
    bgColor: "#fafafa",
  },
  {
    id: 3,
    img: AirpodsImg,
    title: "Buy a Tablet or xPhone for college. \n Get airPods",
    desc: "",
    color: "#000",
    bgColor: "#f1f1f3",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel items={items} stayTime={3} />
    </div>
  );
}

export default App;
