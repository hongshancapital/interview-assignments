import "./App.scss";

import Carousel from "./components/Carousel";
import Img1 from "./assets/airpods.png";
import Img2 from "./assets/iphone.png";
import Img3 from "./assets/tablet.png";

const data = [
  {
    title: ["xPhone"],
    desc: ["Lots to Love. Less to spend.", "Starting at $399"],
    imgSrc: Img2,
    id: 1,
    color: "#fff",
  },
  {
    title: ["Tablet"],
    desc: ["Just the right amount of everything."],
    imgSrc: Img1,
    id: 2,
    color: "#000",
  },
  {
    title: ["Bug a Tablet or xPhone for college.", "Get arPods"],
    desc: [],
    imgSrc: Img3,
    id: 3,
    color: "#000",
  },
];

function App() {
  const renderList = () => {
    return data.map(({ title, imgSrc, desc, id, color }) => {
      const getTitle = () =>
        title.map((text) => (
          <>
            {text}
            <br />
          </>
        ));
      const getDesc = () =>
        desc.map((text) => (
          <>
            {text}
            <br />
          </>
        ));
      return (
        <div className="list-item" key={id}>
          <div className="list-item-detail" style={{ color }}>
            <h1>{getTitle()}</h1>
            {desc.length ? <p>{getDesc()}</p> : ""}
          </div>
          <img src={imgSrc} alt="any" />
        </div>
      );
    });
  };

  return (
    <div className="App">
      <Carousel autoplay>{renderList()}</Carousel>
    </div>
  );
}

export default App;
