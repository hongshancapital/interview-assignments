import "./App.css"

import Carousel from "./components/Carousel"
import { PageItem } from "./components/common/PageItem"
import React from "react"
import { list } from "./common/data"

function App() {
  return (
    <div className="App">
      <Carousel duration={0.5} showTime={2}>
        {list.map((data, index) => (
          <PageItem key={index} data={data} />
        ))}
      </Carousel>
    </div>
  )
}

export default App
