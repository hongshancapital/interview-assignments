import "./App.css";
import Carousel from "./components/carousel";

import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

function App() {
  const data = [
    {
      title: "xPhone",
      fontColor: "#fff",
      des: "Lots to love.Less to spend.\n Starting at $399.",
      imgUrl: iphoneImg,
    },
    {
      title: "Tablet",
      fontColor: "#000000",
      des: "Just the right amount of everything.",
      backgroundColor: "#f9f9f9",
      imgUrl: tabletImg,
    },
    {
      title: "Buy a Tablel or xPhone for collage.\nGet airPods.",
      fontColor: "#000000",
      imgUrl: airpodsImg,
    },
  ];
  console.log(data);
  return (
    <div>
      <div className="carousel-header">
        <Carousel>
          {data.map((item) => (
            <div
              className="container"
              style={{
                color: item.fontColor,
                backgroundImage: `url(${item.imgUrl})`,
              }}
              key={item.title}
            >
              <div className="title">{item.title}</div>
              <div className="des">{item.des}</div>
            </div>
          ))}
        </Carousel>
      </div>
    </div>
  );
}

export default App;
