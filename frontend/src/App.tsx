import './App.css';
import Carousel from './components/Carousel';
import Pannel, { PannelProps } from './components/Pannel';

import airpodsUrl from './assets/airpods.png';
import iphoneUrl from './assets/iphone.png';
import tabletUrl from './assets/tablet.png';

const datas: Array<PannelProps> = [
  {
    style: {
      backgroundColor: '#121212',
      color: '#FFF'
    },
    title: 'xPhone',
    subTitle: (
      <>
        <div>Lots to Love. Less to spend.</div>
        <div>Starting at $399.</div>
      </>
    ),
    imgUrl: iphoneUrl
  },
  {
    style: {
      backgroundColor: '#f9f9f9',
      color: '#000'
    },
    title: 'Tablet',
    imgUrl: tabletUrl
  },
  {
    style: {
      backgroundColor: '#efeff1',
    },
    title: 'xPhone',
    subTitle: (
      <>
        <div>Buy a Tablet or XPhone for college.</div>
        <div>Get arPods.</div>
      </>
    ),
    imgUrl: airpodsUrl
  }
]

function App() {
  return (
    <div className='App'>
      <Carousel>
        {
          datas.map((data, index) => (
            <Pannel key={index} {...data} />
          ))
        }
      </Carousel>
    </div>
  );
}

export default App;
