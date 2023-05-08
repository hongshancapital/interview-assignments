import './App.css';
import Carousel from './components/Carousel';
import Pannel from './components/Pannel';

import airpodsUrl from './assets/airpods.png';
import iphoneUrl from './assets/iphone.png';
import tabletUrl from './assets/tablet.png';

function App() {
  return <div className='App'>
    <Carousel>
      <Pannel
        style={{
          backgroundColor: '#121212',
          color: '#FFF'
        }}
        title='xPhone'
        subTitle={
          <>
            <div>Lots to Love. Less to spend.</div>
            <div>Starting at $399.</div>
          </>
        }
        imgUrl={iphoneUrl}
      />
      <Pannel
        style={{
          backgroundColor: '#f9f9f9',
          color: '#000'
        }}
        title='Tablet'
        subTitle='Just the right amount of everything.'
        imgUrl={tabletUrl}
      />
      <Pannel
        style={{
          backgroundColor: '#efeff1',
        }}
        title={
          <>
            <div>Buy a Tablet or XPhone for college.</div>
            <div>Get arPods.</div>
          </>
        }
        imgUrl={airpodsUrl}
      />
    </Carousel>
  </div>;
}

export default App;
