import './App.css';
import Carousel from './Carousel';
import iphone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png"
function App() {
  return (
    <div className="App">
      <Carousel>
        <div
          className="content xphone"
          style={{
            backgroundImage: `url(${iphone})`,
          }}
        >
          <div className="title">xPhone</div>
          <div>Lots to love.Less to spend.</div>
          <div>Starting at $399.</div>
        </div>
        <div
          className="content tablet"
          style={{
            backgroundImage: `url(${tablet})`,
          }}
        >
          <div className="title">Tablet</div>
          <div>Just the right amount of everything.</div>
        </div>
        <div
          className="content airpods"
          style={{
            backgroundImage: `url(${airpods})`,
          }}
        >
          <div className="title">Buy a Tablet or xPhone for college.</div>
          <div className="title">Get arPods.</div>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
