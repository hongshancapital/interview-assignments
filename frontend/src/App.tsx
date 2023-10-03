import { useEffect, useState } from "react";
import "./App.css";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";
import Carousel from "./components/Carousel";

interface IBannerItem {
  id: string;
  titleList?: string[];
  descriptionList?: string[];
  imageSource?: "iphone" | "tablet" | "airpods";
  mode?: "black" | "white";
}
function App() {
  const [carouselList, setCarouselList] = useState<IBannerItem[]>([]);

  const mockQueryList = () =>
    new Promise<IBannerItem[]>((resolve) => {
      setTimeout(() => {
        resolve([
          {
            id: "01",
            titleList: ["xPhone"],
            descriptionList: [
              "Lots to love. Less to spend.",
              "Starting at $399.",
            ],
            imageSource: "iphone",
            mode: "white",
          },
          {
            id: "02",
            titleList: ["Tablet"],
            descriptionList: ["Just the right amount of evething."],
            imageSource: "tablet",
            mode: "black",
          },
          {
            id: "03",
            titleList: ["Buy a Tablet or xPhone for college.", "Get arPods."],
            descriptionList: [],
            imageSource: "airpods",
            mode: "black",
          },
        ]);
      }, 1000);
    });

  // 模拟接口请求
  useEffect(() => {
    mockQueryList().then((resList) => {
      setCarouselList(resList);
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // Banner 组件（实际的轮播内容）由业务组件随意自定义
  const BannerDemo = (props: IBannerItem) => {
    const {
      titleList = [],
      descriptionList = [],
      imageSource = "",
      mode = "white",
    } = props || {};

    const imageConfig = { iphone, tablet, airpods };
    const colorConfig = { white: "#fff", black: "#333" };

    return (
      <div className="banner-wrapper">
        <div className="banner-content" style={{ color: colorConfig[mode] }}>
          {titleList.map((title, index) => (
            <header key={index} className="title">
              {title}
            </header>
          ))}

          {descriptionList.map((description, index) => (
            <section key={index} className="description">
              {description}
            </section>
          ))}
        </div>

        {imageSource && imageConfig[imageSource] && (
          <img alt="实物图" className="image" src={imageConfig[imageSource]} />
        )}
      </div>
    );
  };

  return (
    <div className="App">
      <Carousel>
        {carouselList &&
          carouselList.map((item) => <BannerDemo key={item.id} {...item} />)}
      </Carousel>

      <footer style={{ display: "none" }}>learn react</footer>
    </div>
  );
}

export default App;
