import { useMemo } from 'react';
import './App.css';
import { Carousel } from './components/carousel/Carousel';
import { CardLayout } from './components/cardLayout/CardLayout';
import { Theme } from './components/cardLayout/types';

function App() {
  const InfoList = useMemo(() => {
    return [
      {
        title: 'xPhone',
        tips: 'Lots to love. Less to spend',
        theme: Theme.BlackTheme,
        image: <img src={require('./assets/iphone.png')} alt="" style={{width: '50%', position: 'absolute', bottom: '150px',left: '25%' }}></img>
      },
      {
        title: 'Tablet',
        tips: 'Just the right amount of everything.',
        theme: Theme.WhiteTheme,
        image: <img src={require('./assets/tablet.png')} alt="" style={{width: '100%', position: 'absolute', bottom: '150px', left: 0}}></img>
      },
      {
        title: `Buy a Tablet or Phone for college.\nGet arPods.`,
        theme: Theme.GrayTheme,
        image: <img src={require('./assets/airpods.png')} alt="" style={{width: '100%', position: 'absolute', bottom: '150px', left: 0}}></img>
      }
    ]
  }, [])
  return <div className='App'>
    <Carousel>
        {
          InfoList.map((info, idx) =>  <CardLayout
            key={idx}
            info={{ title: info.title, tips: info.tips }}
            image={ info.image }
            theme={ info.theme } />
          )
        }
    </Carousel>
  </div>;
}

export default App;
