import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

interface DataType {
    title: string;
    desc: string;
    image: string;
}

const displayData: DataType[] = [
    {
        title: "xPhone",
        desc: "Lots to love. Less to spend. \n Starting at $399.",
        image: iphoneImg
    },
    {
        title: "Tablet",
        desc: "Just the right amount of everything.",
        image: tabletImg
    },
    {
        title: "Buy a Tablet or xPhone for college. \n Get airpods.",
        desc: "",
        image: airpodsImg
    }
];

function App() {
    return (
        <div className="App">
            <Carousel autoplay={3000}>
                {displayData.map((item, index) => (
                    <div className="display-item" key={index}>
                        <div className="title text-sty">{item.title}</div>
                        <div className="text text-sty">{item.desc}</div>
                        <img
                            src={item.image}
                            alt={item.title}
                        />
                    </div>
                ))}
            </Carousel>
        </div>
    );
}

export default App;
