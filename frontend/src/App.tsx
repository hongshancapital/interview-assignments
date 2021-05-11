import React from 'react'
import './App.scss'
import Carousel from './libs/Carousel/index'
import styles from './banners.module.scss'
import airPodImg from './assets/airpods.png'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'

function App() {
  return (
    <div className="App">
      <Carousel>
        <Banner1 />
        <Banner2 />
        <Banner3 />
      </Carousel>
    </div>
  )
}

const Banner1 = () => (
  <div className={`${styles.banner} ${styles.bgBlack}`}>
    <h1>xPhone</h1>
    <p>Lots to love, less to spend.</p>
    <p>Starting as $399.</p>
    <img src={iphoneImg} alt="iphone" className={styles.iphone} />
  </div>
)

const Banner2 = () => (
  <div className={`${styles.banner} ${styles.bgWhite}`}>
    <h1>Tablet</h1>
    <p>Just the right amount of everything.</p>
    <img src={tabletImg} alt="tablet" className={styles.tablet} />
  </div>
)

const Banner3 = () => (
  <div className={`${styles.banner} ${styles.bgGrey}`}>
    <h1>Buy a Tablet or xPhone for college.</h1>
    <h1>Get airPods</h1>
    <img src={airPodImg} alt="airPods" className={styles.airPods} />
  </div>
)

export default App
