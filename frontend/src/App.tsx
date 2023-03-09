import './App.css'
import Carousel from './components/carousel/Carousel'

import iphoneImage from './assets/iphone.png'
import tabletImage from './assets/tablet.png'
import airpodsImage from './assets/airpods.png'
import CarouselBanner, { CarouselBannerProps } from './components/carousel/CarouselBanner'

const CarouselDataList: (CarouselBannerProps & { id: string })[] = [
  {
    id: '1',
    title: ['xPhone'],
    subTitle: ['Lots to love.Less to spend.', 'Starting at $399'],
    productImageSrc: iphoneImage,
    textColor: '#fff',
    backgroundColor: '#111',
  },
  {
    id: '2',
    title: ['Tablet'],
    subTitle: ['Just the right amount of everything.'],
    textColor: '#000',
    backgroundColor: '#fafafa',
    productImageSrc: tabletImage,
  },
  {
    id: '3',
    title: ['Buy a Tablet or xPhone for college.', 'get arPods.'],
    textColor: '#000',
    backgroundColor: '#f1f1f3',
    productImageSrc: airpodsImage,
  },
]
function App() {
  return (
    <div className="App">
      <Carousel>
        {CarouselDataList.map((carouselData) => {
          return (
            <CarouselBanner
              key={carouselData.id}
              {...carouselData}
            ></CarouselBanner>
          )
        })}
      </Carousel>
    </div>
  )
}

export default App
