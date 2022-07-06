import React from "react";
import "./App.scss";
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";

interface IProduct {
  title: string[];
  descriptions?: string[];
  imgUrl: string;
}

const products: IProduct[] = [
  {
    title: ["xPhone"],
    descriptions: ["Lots to love. Less to spend.", "Starting at $399"],
    imgUrl: iphone,
  },
  {
    title: ["tablet"],
    descriptions: ["Just the right amout of everything."],
    imgUrl: tablet,
  },
  {
    title: ["Buy a Tablet or xPhone for college.", "Get arPods"],
    imgUrl: airpods,
  },
];

function App() {
  return (
    <div className="App" style={{ width: "100vw", height: "100vh" }}>
      <Carousel>
        {products.map((item, idx) => (
          <div
            key={idx}
            className="product-item"
            style={{
              width: "100%",
              height: "100%",
              color: idx === 0 ? "#fff" : "#000",
            }}
          >
            <div className="product-item__content">
              {item.title.map((t, tIdx) => (
                <div className="title" key={tIdx}>
                  {t}
                </div>
              ))}
              {item.descriptions?.map?.((desc, dIdx) => (
                <div className="text" key={dIdx}>
                  {desc}
                </div>
              ))}
            </div>
            <img src={item.imgUrl} alt="img-alt" />
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
