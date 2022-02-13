import React, { useState } from "react";
import "./App.css";
import { Carousel, SlideItem } from "./components/Carousel";
import { Slide001, Slide002, Slide003 } from "./components/slides";

//#region  slides components defined later.

function App() {
  const [slides] = useState<SlideItem[]>(() => {
    return [
      { id: "slide01", content: <Slide001 /> },
      { id: "slide02", content: <Slide002 /> },
      { id: "slide03", content: <Slide003 /> },
    ];
  });

  return (
    <div className="App">
      <Carousel slides={slides} timingDur={6000} />
    </div>
  );
}

export default App;
