import ImgIPhone from "./assets/iphone.png"
import ImgTablet from "./assets/tablet.png"
import ImgAirpods from "./assets/airpods.png"
import Carousel, { Props as CarouselProps } from "./components/Carousel"

import "./App.css"

const carouselDataList: CarouselProps["dataList"] = [
  {
    id: "iphone",
    backgroundImage: ImgIPhone,
    title: "xPhone",
    description: ["Lots to love. Less to spend.", "Staring as $e99"],
    style: {
      "--width": "100vw",
      "--height": "100vh",
      "--text-color": "#fff",
      "--image-size": "container",
    },
  },
  {
    id: "tablet",
    backgroundImage: ImgTablet,
    title: "Tablet",
    description: "Just the right amount of everything",
    style: {
      "--width": "100vw",
      "--height": "100vh",
    },
  },
  {
    id: "arPod",
    backgroundImage: ImgAirpods,
    title: ["Buy a Tablet or xPhone for college", "Get arPods."],
    style: {
      "--width": "100vw",
      "--height": "100vh",
    },
  },
]

function App() {
  return (
    <div className="App">
      <Carousel dataList={carouselDataList} />
    </div>
  )
}

export default App
