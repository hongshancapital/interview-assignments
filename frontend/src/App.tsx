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

const CarouselItem = (props: CarouselItemProps) => {
  const { className, titles, descriptions, src , key} = props
   return <div className={className} key={key}>
     {titles.map((t,index) =><p className={`title title-${index}`} key={index}>{t}</p>)}
     {descriptions.map((d,index) =><p className={`desc desc-${index}`} key={index}>{d}</p>)}
     <div className='img-wrap'>
      <img src={src} alt=''/>
     </div>
   </div>
}

function App() {
  const renderItems = () => {
    return [
     <CarouselItem src={iphone} className='iphone' key={1} titles={['xPhone']} descriptions={['Lots to love.Less to Spend.', 'Starting at $399.']}/>,
     <CarouselItem src={airPods} className='airPods' key={2} titles={['Buy a Tablet or Xphone for college.', 'Get arPods.']} descriptions={[]}/>,
     <CarouselItem src={tablet} className='tablet' key={3} titles={['Tablet']} descriptions={['Just the right amount of everything.']} />,
    ]
  }
  return (
    <div className="App">
      <Carousel duration={5000}>{renderItems()}</Carousel>
    </div>
  )
}

export default App
