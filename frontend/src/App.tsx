import React, { Fragment, useState } from "react"
import "./App.css"
import airpods from "./assets/airpods.png"
import iphone from "./assets/iphone.png"
import tablet from "./assets/tablet.png"
import Shuffling from "./components/shuffling"
import { ShufflingData } from "./components/shuffling/types"

function App() {
  const shufflingItemData: Array<ShufflingData> = [
    {
      title: "xPhone",
      text: "Lots to love.Less to spend.<br />Starting at $399.",
      img: iphone,
      imgStyle: {
        backgroundSize: "396%",
        backgroundPosition: "-292px -300px",
      },
      style: {
        background: "#111111",
        color: "#fff",
      },
    },
    {
      title: "Tablet",
      text: "Just the right amount of everything.",
      img: tablet,
      imgStyle: {
        backgroundSize: "826%",
        backgroundPosition: "-726px -314px",
      },
      style: {
        background: "#fafafa",
        color: "#000",
      },
    },
    {
      title: "Buy a Tablet or xPhone for college.<br />Get airPods.",
      text: "",
      img: airpods,
      imgStyle: {
        backgroundSize: "826%",
        backgroundPosition: "-730px -172px",
      },
      style: {
        background: "#f1f1f3",
        color: "#000",
      },
    },
  ]

  return (
    <div className="App">
      <Shuffling data={shufflingItemData} intervalTime={2400}></Shuffling>
    </div>
  )
}

export default App
