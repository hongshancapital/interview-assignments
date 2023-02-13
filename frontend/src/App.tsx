import './App.css';

import Carousel from './components/carousel/carousel'

import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'

interface IItemInfo {
  title: string,
  text?: string,
  imageUrl: string,
  dark?: boolean
}

function App() {
  const itemList: Array<IItemInfo> = [
    {
      title: 'XPhone',
      text: 'Lots to love. Less to spend.<br>Starting at $399.',
      imageUrl: iphoneImg,
      dark: true
    },
    {
      title: 'Tablet',
      text: 'Just the right amount of everything.',
      imageUrl: tabletImg
    },
    {
      title: 'Buy a Tablel or xPhone for collage.<br/>Get airPods.',
      imageUrl: airpodsImg
    }
  ]

  return <div className='App'>
    <Carousel>
      <>
        {itemList.map((item, index) =>
          <Carousel.Item key={index}>
            <>
              <div className="img">
                <img src={item.imageUrl} alt="xPhone" />
              </div>
              <div className="infomation">
                <h4 className={`title ${item.dark? 'text-light': ''}`} dangerouslySetInnerHTML={{__html: item.title}}></h4>
                {item.text && <p className={`text ${item.dark? 'text-light': ''}`} dangerouslySetInnerHTML={{__html: item.text}}></p>}
              </div>
            </>
          </Carousel.Item>
        )}
      </>
    </Carousel>
  </div>;
}

export default App;
