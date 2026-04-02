import "./App.css";
import Carousel from "./carousel/index";
function App() {
  return (
    <div className="App">
      <Carousel height={600} width={1000} duration={3000} btnColors ={['white','grey']}>
        <Carousel.Paper>
          <div className="banner iphone">
            <div className="title">xPhone</div>
            <div className="text">Lots to love.Less to spend.</div>
            <div className="text">Starting at $399.</div>
          </div>
        </Carousel.Paper>
        <Carousel.Paper>
          <div className="banner tablet">
            <div className="title">tablet</div>
            <div className="text">Just the right amount of everything.</div>
          </div>
        </Carousel.Paper>
        <Carousel.Paper>
          <div className="banner airpods">
             <div className="title">Buy a Tablet or xPhone for college.</div>
             <div className="title">Get arPods.</div>
          </div>
        </Carousel.Paper>
      </Carousel>
    </div>
  );
}

export default App;
