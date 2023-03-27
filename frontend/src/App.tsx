import './App.css';

// 开发中，这里的图片应该要重新切图，这里图片高度一致，宽度不一致，是考场background的应用？
import iphoneSrc from './assets/iphone.png';
import airpodsSrc from './assets/airpods.png';
import tabletSrc from './assets/tablet.png';

import Carousel from './components/Carousel';
import SellPoster from './components/SellPoster';

function App() {
  return <div className='App'>
    <Carousel autoPlay={true} width={1600} height={900} carouselItems={[
      () => <SellPoster width='100%' height='100%' mode='dark' bgColor='rgb(17, 17, 17)' title={'xPhone'} description={['Lots to love. Less to spend.', 'Starting at $399.']} imgSrc={iphoneSrc}></SellPoster>,
      () => <SellPoster width='100%' height='100%' mode='light' bgColor='white' title={'Tablet'} description={'Just the right amount of everything.'} imgSrc={tabletSrc}></SellPoster>,
      () => <SellPoster width='100%' height='100%' mode='light' bgColor='rgb(241, 241, 241)' title={['Buy a Tablet or xPhone for college.', 'Get airPods.']} imgSrc={airpodsSrc}></SellPoster>,
    ]}></Carousel>
  </div>;
}

export default App;
