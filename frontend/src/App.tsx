import styles from './app.module.scss'
import Carousel from './components/Carousel'
import airpodsImg from './assets/airpods.png'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import CarouselItem from './components/CarouselItem'
import { CSSProperties } from 'react'
import classnames from 'classnames'

function App() {
  const carouselItemStyle: CSSProperties = {
    width: '1000px',
    height: '700px'
  }

  return (
    <div className={styles.app}>
      <Carousel autoplay>
        <CarouselItem img={iphoneImg} style={carouselItemStyle}>
          <section className={styles.content}>
            <h2 className={classnames(styles.title, styles.white)}>xPhone</h2>
            <p className={classnames(styles.description, styles.white)}>
              Lots to love. Less to spend.
            </p>
            <p className={classnames(styles.description, styles.white)}>Staring at $399.</p>
          </section>
        </CarouselItem>
        <CarouselItem img={tabletImg} style={carouselItemStyle}>
          <section className={styles.content}>
            <h2 className={styles.title}>Tablet</h2>
            <p className={styles.description}>Just the right amount of everything</p>
          </section>
        </CarouselItem>
        <CarouselItem img={airpodsImg} style={carouselItemStyle}>
          <section className={styles.content}>
            <h2 className={styles.title}>Buy a Tablet or xPhone for college.</h2>
            <h2 className={styles.title}>Get arPods.</h2>
          </section>
        </CarouselItem>
      </Carousel>
    </div>
  )
}

export default App
