import './App.css';
import { FunctionComponent } from 'react';
import Carousel from './components/carousel'
import imgIphone from "./assets/iphone.png";
import imgTablet from "./assets/tablet.png";
import imgAirpods from "./assets/airpods.png";


const IphoneView: FunctionComponent<{}> = () => {
  return (
    <div className="page-container page-iphone">
      <h1 className="page-text-content page-iphone-text page-title">xPhone</h1>
      <h2 className="page-text-content page-iphone-text page-subtitle">
        Lots to Love. Less to Spend.
      </h2>
      <h2 className="page-text-content page-iphone-text page-subtitle">
        Starting at $399.
      </h2>
      <img src={imgIphone} className="bottom-img" alt="iphone" />
    </div>
  );
}

const TabletView: FunctionComponent<{}> = () => {
  return (
    <div className="page-container page-tablet">
      <h1 className="page-text-content page-tablet-text page-title">Tablet</h1>
      <h2 className="page-text-content page-tablet-text page-subtitle">
        Just the right amount of everything.
      </h2>
      <img src={imgTablet} className="bottom-img tablet-img" alt="Tablet" />
    </div>
  );
}

const AirpodsView: FunctionComponent<{}> = () => {
  return (
    <div className="page-container page-airpods">
      <h1 className="page-text-content page-title page-airpods-text ">
        Buy a Tablet or xPhone for College.
      </h1>
      <h1 className="page-text-content page-title page-airpods-text  page-airpods-second-title">
        Get arPods
      </h1>
      <img src={imgAirpods} className="bottom-img airpods-img" alt="airpods" />
    </div>
  );
};

function App() {
  return (
      <Carousel>
        <IphoneView />
        <TabletView />
        <AirpodsView />
      </Carousel>
  )
}

export default App;
