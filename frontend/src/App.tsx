import "./App.scss";
import Carousel from "./components/Carousel";

function App() {
  return (
    <div className="App">
      <Carousel
        style={{ height: "100vh" }}
        autoplay
        showPagination
        waitTime={5e3}>
        <Carousel.Slider>
          <div className="iphone">
            <div className="title ">xPhone</div>
            <div className="txt">
              Lots to love. Less to spend.<br></br> Starting at $ 399.
            </div>
          </div>
        </Carousel.Slider>
        <Carousel.Slider>
          <div className="tablet">
            <div className="title ">Tablet</div>
            <div className="txt">Just the right amount of everything.</div>
          </div>
        </Carousel.Slider>
        <Carousel.Slider>
          <div className="airpods">
            <div className="title">
              Buy a Tablet or xPhone for college.<br></br>Get arPods.
            </div>
          </div>
        </Carousel.Slider>
      </Carousel>
    </div>
  );
}

export default App;
