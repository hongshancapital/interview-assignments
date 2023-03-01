import Carousel from './components/carousel'
import { getBannerList } from './mock/banner';

import './App.css';
import classNames from 'classnames';

const bannerList = getBannerList()

function App() {
  return (
    <div className='App'>
      <Carousel>
        {
          bannerList.map(item => {
            return (
              <div
                className={classNames('banner', { 'text-dark': item.isDark })}
                key={item.title}
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
