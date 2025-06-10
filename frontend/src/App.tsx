import './App.css'
import iphone from './assets/iphone.png'
import airPods from './assets/airpods.png'
import tablet from './assets/tablet.png'
import Carousel from './components/carousel'

interface CarouselItemProps {
  src: string
  titles: string[]
  descriptions: string[]
  className: string
  key: number | string
}

export const CarouselItem = (props: CarouselItemProps) => {
  const { className, titles, descriptions, src, key } = props
  return (
    <div className={`flex-center ${className}`} key={key}>
      {titles.map((t, index) => (
        <p className={`title title-${index}`} key={index}>
          {t}
        </p>
      ))}
      {descriptions.map((d, index) => (
        <p className={`desc desc-${index}`} key={index}>
          {d}
        </p>
      ))}
      <div className="img-wrap flex-center">
        <img src={src} alt="" />
      </div>
    </div>
  )
}
const DATA = [
  {
    src: iphone,
    className: 'iphone',
    titles: ['xPhone'],
    descriptions: ['Lots to love.Less to Spend.', 'Starting at $399.'],
  },
  {
    src: airPods,
    className: 'airPods',
    titles: ['Buy a Tablet or Xphone for college.', 'Get arPods.'],
    descriptions: [],
  },
  {
    src: tablet,
    className: 'tablet',
    titles: ['Tablet'],
    descriptions: ['Just the right amount of everything.'],
  },
]

function App() {
  return (
    <div className="App">
      <Carousel duration={5000}>
        {DATA.map((item, index) => (
          <CarouselItem
            src={item.src}
            className={item.className}
            key={index}
            titles={item.titles}
            descriptions={item.descriptions}
          />
        ))}
      </Carousel>
    </div>
  )
}

export default App
