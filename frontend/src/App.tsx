import { useMemo } from 'react';
import './App.css';
import Banner from './components/Banner';
import Carousel from './components/Carousel';
import { getBannerInfo } from './services';
function App() {
  const bannerInfo = useMemo(() => getBannerInfo(), []);
  return (
    <div className="App">
      <Carousel height="100vh">
        {bannerInfo.map((item) => {
          return <Banner key={item.id} data={item} />;
        })}
      </Carousel>
    </div>
  );
}

export default App;
