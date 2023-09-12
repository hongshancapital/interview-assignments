import "./App.css";
import { Carousel } from "./components/carousel";
import AirpodsImage from "./assets/airpods.png";
import IPhoneImgage from "./assets/iphone.png";
import TabletImage from "./assets/tablet.png";

import type { ICarouselItem } from "./components/carousel";

function App() {
  const items: ICarouselItem[] = [
    {
      title: "xPhone",
      subtitle: "Lots to love. Less to spend. \n Starting at $399.",
      backgroundImage: IPhoneImgage,
      backgroundColor: "black",
    },
    {
      title: "Tablet",
      subtitle: "Just the right amount of everything.",
      backgroundImage: TabletImage,
    },
    {
      title: `Buy a Tablet or xPhone for college. \n Get arPods`,
      backgroundImage: AirpodsImage,
    },
  ];
  return (
    <div className="App" style={{ width: "100vw", height: "100vh" }}>
      <Carousel items={items} autoplay />
    </div>
  );
}

export default App;
