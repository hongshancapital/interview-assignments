import React from "react";
import "./App.css";
import phoneImg from "../src/assets/iphone.png";
import tabletImg from "../src/assets/tablet.png";
import airpodsImg from "../src/assets/airpods.png";
import Carousel from "./components/Carousel";

const imgList = [
  {
    url: phoneImg,
    title: <p>xPhone</p>,
    description: (
      <>
        <p>Lots to love. less to spend</p>
        <p>Starting at $399</p>
      </>
    ),
    color: "#fff",
    backgroundColor: '#111'
  },
  {
    url: tabletImg,
    title: <p>Tablet</p>,
    description: "Just the right amount of everything.",
    color: "#000",
    backgroundColor: '#fafafa'
  },
  {
    url: airpodsImg,
    title: (
      <>
        <p>Buy a Tablet or xPhone for college.</p>
        <p>Get airPods.</p>
      </>
    ),
    color: "#000",
    backgroundColor: '#f1f1f3'
  },
];

function App() {
  return (
    <div className="App">
      <Carousel autoPlay={true}>
        {imgList.map((item, index) => {
          return (
            <div
              key={index}
              style={{
                backgroundImage: `url(${item.url})`,
                backgroundColor: item.backgroundColor,
                color: item.color,
              }}
              className="slide-content"
            >
              <h3>{item.title}</h3>
              <div>{item.description}</div>
            </div>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
