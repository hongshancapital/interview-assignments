import Slider from "./comps";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Slider
        len={3}
        content={
          <>
            <div className="part">
              <div className="text">
                <p className="title">xPhone</p>
                <p className="subtitle">Lots to love.Less to spend.</p>
                <p className="subtitle">Starting at $399.</p>
              </div>
              <div className="img"></div>
            </div>
            <div className="part">
              <div className="text">
                <p className="title">Tablet</p>
                <p className="subtitle">Just the right amount of everything.</p>
              </div>
              <div className="img"></div>
            </div>
            <div className="part">
              <div className="text">
                <p className="title">Buy a Tablet Or xPhone for college.</p>
                <p className="title">Get arPods.</p>
              </div>
              <div className="img"></div>
            </div>
          </>
        }
      ></Slider>
    </div>
  );
}

export default App;
