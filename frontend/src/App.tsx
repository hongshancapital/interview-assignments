import "./App.css";
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";
function App() {
  return (
    <div className="App">
      <Carousel>
        <div className="common iphone">
          <div>
            <h1>xPhone</h1>
            <p>Lots to love. Less to spend.</p>
            <p>Staring at $399.</p>
          </div>
          <img src={iphone} alt="iphone"></img>
        </div>
        <div className="common airpods">
          <div>
            <h1>Table</h1>
            <p>Just the right amount of everything.</p>
          </div>
          <img src={airpods} alt="airpods"></img>
        </div>
        <div className="common tablet">
          <div>
            <h1>Buy a Tablet or xPhone for college.</h1>
            <h1>Get arPods.</h1>
          </div>
          <img src={tablet} alt="tablet"></img>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
