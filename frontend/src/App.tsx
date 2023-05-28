import "./App.css";

import React, { useMemo } from "react";
import Carousel, { CarouselItemType } from "./components/Carousel";

function App() {
    const data = useMemo<CarouselItemType[]>(
        () => [
            {
                imageUrl: require("./assets/iphone.png"),
                title: "xPhone",
                content: "Lots to love.Less to spend.\nStarting at $399.",
                dark: true,
                containerStyle: { backgroundColor: "#111111" },
            },
            {
                imageUrl: require("./assets/tablet.png"),
                title: "Tablet",
                content: "Just the right amount of everything.",
                containerStyle: { backgroundColor: "#fafafa" },
            },
            {
                imageUrl: require("./assets/airpods.png"),
                title: "Buy a Table or xPhone for college.\nGet arPods.",
                containerStyle: { backgroundColor: "#f1f1f3" },
            },
        ],
        []
    );

    return (
        <div className="App">
            <Carousel data={data} />
        </div>
    );
}

export default App;
