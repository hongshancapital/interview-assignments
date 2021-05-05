import React from 'react'
import './App.scss'
import Carousel from './libs/Carousel/index'
import styles from './banners.module.scss'

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
  </div>
)

const Banner2 = () => (
  <div className={`${styles.banner} ${styles.bgWhite}`}>
    <h1>Tablet</h1>
    <p>Just the right amount of everything.</p>
  </div>
)

const Banner3 = () => (
  <div className={`${styles.banner} ${styles.bgGrey}`}>
    <h1>Buy a Tablet or xPhone for college.</h1>
    <h1>Get airPods</h1>
  </div>
)

export default App
