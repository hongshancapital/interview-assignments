import "./App.css";

import { Carousel } from "./components/Carousel";

import png_airpods from "./assets/airpods.png";
import png_iphone from "./assets/iphone.png";
import png_tablet from "./assets/tablet.png";

function App() {
  return (
    <div className="App">
      {/* write your component here */}
      <Carousel duration={2000} className="goods_carousel">
        <div
          className="good"
          style={{ backgroundImage: `url(${png_iphone})`, color: "#fff" }}
        >
          <h1>xPhone</h1>
          <h3>
            Lots to love. Less to spend.
            <br />
            Starting at $399.
          </h3>
        </div>
        <div className="good" style={{ backgroundImage: `url(${png_tablet})` }}>
          <h1>Tablet</h1>
          <h3>Just the right amount of everything.</h3>
        </div>
        <div
          className="good"
          style={{ backgroundImage: `url(${png_airpods})` }}
        >
          <h1>
            Buy a Tablet or xPhone for college.
            <br />
            Get arPods.
          </h1>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
