import React, {useMemo} from "react";
import "./App.css";
import Carousel from "./components/Carousel/Carousel";
import Banner,{BannerProps} from "./components/Banner/Banner";

function App() {

  const bannerList = useMemo<BannerProps[]>(() => {
    return [
      {
        id: 'banner1',
        title: 'xPhone',
        desc: <span>Lots to love.Less to spend <br /> Starting at $399</span>,
        styles: {
          color: '#fff',
          backgroundColor: '#111111',
          backgroundImage: `url(${require('./assets/iphone.png').default})`,
          backgroundSize: '50%',
          backgroundPosition: 'center'
        }
      },
      {
        id: 'banner2',
        title: 'Tablet',
        desc: <span>Just the right amount of everything</span>,
        styles: {
          color: '#333',
          backgroundColor: '#f9f9f9',
          backgroundImage: `url(${require('./assets/tablet.png').default})`,
          backgroundSize: '75%',
          backgroundPosition: 'center 70%'
        }
      },
      {
        id: 'banner3',
        title: <span>Buy a Tablet or xPhone for college. <br /> Get airPods</span>,
        styles: {
          backgroundColor: '#f0f0f2',
          color: '#333',
          backgroundImage: `url(${require('./assets/airpods.png').default})`,
          backgroundSize: '100%',
          backgroundPosition: 'center 72%'
        }
      }
    ]
  }, [])

  return <div className="App">
    <Carousel>
      {
        bannerList.map(item => <Banner {...item}  key={item.id} />)
      }
    </Carousel>
  </div>;
}

export default App;
