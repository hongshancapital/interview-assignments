import React from "react";
import "./App.css";
import posterThree from "./assets/airpods.png";
import posterOne from "./assets/iphone.png";
import posterTwo from "./assets/tablet.png";
import { Carousel } from "./components/Carousel";
import { CarouselOptions } from "./components/Carousel/type";

function App() {
    const options: CarouselOptions[] = [
        {
            backgroundColor: "#101010",
            backgroundImg: posterOne,
            title: "xPhone",
            desc: "Lots to love. Less to spend.\nStarting at $399.",
            color: "#ffffff",
        },
        {
            backgroundColor: "#fafafa",
            backgroundImg: posterTwo,
            title: "Tablet",
            desc: "Just the right amount of everything.",
        },
        {
            backgroundColor: "#f2f2f4",
            backgroundImg: posterThree,
            title: "But a Tablet or xPhone for college.",
        },
    ];

    return (
        <div className="App">
            <Carousel options={options}></Carousel>
        </div>
    );
}

export default App;
