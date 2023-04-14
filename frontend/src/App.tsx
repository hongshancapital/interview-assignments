import "./App.css";
import Carousel from "./components/Carousel";
import airPodsIcon from "./assets/airpods.png";
import iphoneIcon from "./assets/iphone.png";
import tabletIcon from "./assets/tablet.png";

function App() {
  return (
    <div className="App">
      <Carousel deply={2000} speed={0.5}>
        <div className="bannerWrap">
          <span>
            Buy a Tablet or xPhone for college. <br /> Get airPods.
          </span>
          <img src={airPodsIcon} alt="轮播图" className="banner" />
        </div>
        <div className="bannerWrap bannerBlack">
          <span>xPhone</span>
          <span>
            Lots to love.Less to spend. <br /> Startint at $399.
          </span>
          <img src={iphoneIcon} alt="轮播图" className="banner bannerIphone" />
        </div>
        <div className="bannerWrap bannerTablet">
          <span>Tablet</span>
          <span>Just the right amount of everything.</span>
          <img src={tabletIcon} alt="轮播图" className="banner" />
        </div>
      </Carousel>
    </div>
  );
}

export default App;
