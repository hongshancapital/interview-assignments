import React from "react";
import Carousel from "./components/Carousel/Carousel";
import {CarouselConfig as defaultConfig} from "./config/Carousel";
import "./App.css";

function App() {
    return <div className="App">
        <Carousel
            {...defaultConfig}
        />
    </div>;
}

export default App;
