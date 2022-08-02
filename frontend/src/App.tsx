import Styles from './App.module.css'
import resizeRoot from './utils/resizeRoot'
import Carousel, { CarouselItem } from './components/Carousel'
import ios16 from './assets/ios16.jpeg'
import mobile from './assets/mobile.jpeg'
import macos from './assets/macos.jpeg'
resizeRoot()
function App () {
  return (
    <div className={Styles.App}>
      <Carousel className={Styles.Carousel} data-testid='Carousel'>
        <CarouselItem className={Styles.item}>
          <div className={Styles.content}>
            <div className={Styles.demoText}>The Demo Text</div>
          </div>
          <img src={ios16} alt='carousel-img' />
        </CarouselItem>
        <CarouselItem className={Styles.item}>
          <img src={mobile} alt='carousel-img' />
        </CarouselItem>
        <CarouselItem className={Styles.item}>
          <img src={macos} alt='carousel-img' />
        </CarouselItem>
      </Carousel>
    </div>
  )
}

export default App
