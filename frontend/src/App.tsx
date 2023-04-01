import React from "react";
import "./App.css";
import Carousel, {
  Item,
  Info,
  InfoProps,
} from "./components/Carousel/Carousel";

import airpods from "./assets/airpods.png";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";

function App() {
  const mock: InfoProps[] = [
    {
      title: "Iphone",
      description: "Lots to love",
      imageUrl: iphone,
      backgroundColor: "#111",
      color: "#fff",
    },
    {
      title: "Airpods",
      description: "Lots to love",
      imageUrl: airpods,
      backgroundColor: "#f0f0f0",
      color: "#000",
    },
    {
      title: "Tablet",
      description: "Lots to love",
      imageUrl: tablet,
      backgroundColor: "#fafafa",
      color: "#000",
    },
  ];

  return (
    <div className="App">
      <Carousel timeout={5}>
        {mock.map((i, index) => (
          <Item
            key={index}
            style={{
              backgroundColor: i.backgroundColor,
            }}
          >
            <Info
              title={i.title}
              description={i.description}
              imageUrl={i.imageUrl}
              backgroundColor={i.backgroundColor}
              color={i.color}
            />
          </Item>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
