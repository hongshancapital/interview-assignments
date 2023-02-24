import Carousel from './components/carousel'
import { getBannerList } from './mock/banner';

import './App.css';

function App() {
  // return <div className='App'>{/* write your component here */}</div>;
  const bannerList = getBannerList()
  const color = (val: boolean) => val ? '#fff' : '#333'

  return (
    <div className='App'>
      <Carousel>
        {
          bannerList.map(item => {
            return (
              <div
                className='banner'
                key={item.title}
                style={{ color: color(item.light) }}
              >
                <div className="banner-header">
                  <div className="banner-header__title">
                    {item.title}
                  </div>
                  <div className="banner-header__desc">{item.desc}</div>
                </div>
                <img src={item.url} alt={item.desc} />
              </div>
            )
          })
        }
      </Carousel>
    </div>
  )
}

export default App;
