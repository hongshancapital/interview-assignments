import Carousel from './components/Carousel'
import Iphone from './assets/iphone.png'
import Tablet from './assets/tablet.png'
import Airpods from './assets/airpods.png'
import IPhonePage from './pages/IPhonePage'
import TabletPage from './pages/TabletPage'
import AirpodsPage from './pages/AirpodsPage'
import './App.css';

function App() {
  const imgList = [
    { text: 'xPhone', bg: Iphone, content: <IPhonePage /> },
    { text: 'Tablet', bg: Tablet, content: <TabletPage /> },
    { text: 'Buy', bg: Airpods, content: <AirpodsPage /> },
  ]

  return (
    <div className='App'>
      <Carousel className="carousel-wrapper">
        {
          imgList.map((item, index) => {
            return (
              <div key={index}>
                <img className="img" src={item.bg} alt="" />
                <div>
                  {item.content}
                </div>
              </div>
            )
          })
        }
      </Carousel>
    </div>
  )
}

export default App;
