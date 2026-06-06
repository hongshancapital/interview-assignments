import "./App.css";
import { Carousel } from "./components/carousel";

function App() {
  return (
    <div className="App">
      <Carousel height={500} duration={3000} autoplay>
        <Carousel.Item>
          <div className="iphone">
            <div className="iphone-title">xPhone</div>
            <div>Lots to love. Less to spend.</div>
            <div>Starting at $399.</div>
          </div>
        </Carousel.Item>
        <Carousel.Item>
          <div className="tablet">
            <div className="tablet-title">Tablet</div>
            <div>Just the right amount of everything.</div>
          </div>
        </Carousel.Item>
        <Carousel.Item>
          <div className="airpods">
            <div className="airpods-title">Buy a Tablet or xPhone for College.</div>
            <div className="airpods-title">Get arPods.</div>
          </div>
        </Carousel.Item>
      </Carousel>
    </div>
  );
}

export default App;
