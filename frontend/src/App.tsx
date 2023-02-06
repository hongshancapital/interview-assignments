import { useMemo } from "react"
import "./App.css"
import Carousel from "./Carousel"
import type { CarouselType } from "./Carousel"
import iphoneImg from "./assets/iphone.png"
import tabletImg from "./assets/tablet.png"
import airpodsImg from "./assets/airpods.png"

function App() {
  const list = useMemo<CarouselType[]>(() => {
    return [
      {
        id: 1,
        imgUrl: iphoneImg,
        title: ["XPhone"],
        description: ["Lot's to Love.Less to send", "Starting at $399."],
        color: "#fff",
      },
      {
        id: 2,
        imgUrl: tabletImg,
        title: ["Tablet"],
        description: ["just the right amount of everything."],
        color: "#000",
      },
      {
        id: 3,
        imgUrl: airpodsImg,
        title: ["Buy a Tablet or XPhone for collage.", "Get airPods"],
        description: [],
        color: "#000",
      },
    ]
  }, [])

  return (
    <div className="App">
      <Carousel list={list} height={"500px"} width={"1000px"}></Carousel>
    </div>
  )
}

export default App
