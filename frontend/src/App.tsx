import React, { useMemo } from "react";
import "./App.css";
import Carousel, {ImgInfo} from "./carousel";
function App() {
  const items = useMemo(() => {
    let items: ImgInfo[] =
        [
          {
            key: 'iphone',
            //图片地址
            imgUrl: require("./assets/iphone.png"),
            //图片文字描述
            label: ['xPhone'],
            note: ['Lots to love. Less to spend.', 'Starting at $399'],
            color: 'white',
          },
          {
            key: 'tablet',
            //图片地址
            imgUrl: require("./assets/tablet.png"),
            //图片文字描述
            label: ['Tablet'],
            note: ['Just the right amount of everything.']
          },
          {
            key: 'airpods',
            //图片地址
            imgUrl: require("./assets/airpods.png"),
            //图片文字描述
            label: ["Buy a Tablet or xPhone for college", "Get airPods."],
          }
        ]
    ;
    return items;
  }, []);

  return (
      <div className="App">
        <Carousel
            duration={3000}
            items={items}
        ></Carousel>
      </div>
  );
}

export default App;
