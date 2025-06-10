import "./App.css";

import airpds from "./assets/airpods_8.png";
import iphone from "./assets/iphone_8.png";
import tablet from "./assets/tablet_8.png";

import Carousel, { Banner_Inter } from "./components/Carousel";
import { Them_Enum } from "./components/Carousel/Banner";

/**
 * 轮播图数据
 */
const bannerList: Banner_Inter[] = [
  {
    id: 1,
    imgUrl: iphone,
    backgroundColor: "#111111",
    title: ["xPhone"],
    text: ["Lost to love. Less to spend.", "Starting at $399."],
    them: Them_Enum.dark,
  },
  {
    id: 2,
    imgUrl: tablet,
    backgroundColor: "#f9f9f9",
    title: ["Tablet"],
    text: ["Just the right amount of everything."],
  },
  {
    id: 3,
    imgUrl: airpds,
    backgroundColor: "#f0f0f2",
    title: ["Buy a Table or xPhone for college.", "Get arPods."],
  },
];

function App() {
  return (
    <div className="App">
      <Carousel list={bannerList} />
    </div>
  );
}

export default App;
