import './App.css';
import { SlideShow, Slide } from './components/slide-show';
import iphonePng from './assets/iphone.png';
import tabletPng from './assets/tablet.png';
import airpodsPng from './assets/airpods.png';

function App() {
  return <div className='App'>
    <SlideShow height="500px" width="100vw" time={3000}>
      <Slide height="500px" width="100vw" title="xPhone" subTitle={'Lots to love. less to spend.\nStarting at $399'} bgPic={iphonePng} color="white" bgColor="#111"/>
      <Slide height="500px" width="100vw" title="Tablet" subTitle="Just the right amount of everything." bgPic={tabletPng}/>
      <Slide height="500px" width="100vw" title={'Buy a Tablet or xPhone for college.\nGet arPods.'} bgPic={airpodsPng}/>
    </SlideShow>
  </div>;
}

export default App;
