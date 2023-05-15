import Carousel from './components/Carousel/index'
import imgIPhone from './assets/iphone.png'
import imgTablet from './assets/tablet.png'
import imgAirPods from './assets/airpods.png'

import './App.css'

interface CarouselDataItem {
  id: string
  color: string
  backgroundColor: string
  headline: string[]
  eyebrow?: string[]
  bg: string
}

export const data: CarouselDataItem[] = [
  {
    id: '1',
    color: '#fff',
    backgroundColor: '#111111',
    headline: ['xPhone'],
    eyebrow: ['Lots to love. Less to spend.', 'Starting at $399.'],
    bg: imgIPhone
  },
  {
    id: '2',
    color: '#000',
    backgroundColor: '#FAFAFA',
    headline: ['Tablet'],
    eyebrow: ['Just the right amount of everything.'],
    bg: imgTablet
  },
  {
    id: '3',
    color: '#000',
    backgroundColor: '#F1F1F1',
    headline: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
    bg: imgAirPods
  }
]

function App() {
  return (
    <div className="App">
      <Carousel
        items={data.map(item => ({
          key: item.id,
          render() {
            return (
              <div
                className="slide"
                style={{
                  backgroundColor: item.backgroundColor,
                  backgroundImage: `url(${item.bg})`
                }}
              >
                <div className="text-content" style={{ color: item.color }}>
                  <h2 className="headline">{item.headline.join('\r\n')}</h2>
                  {item.eyebrow && <p className="eyebrow">{item.eyebrow.join('\r\n')}</p>}
                </div>
              </div>
            )
          }
        }))}
      />
    </div>
  )
}

export default App
