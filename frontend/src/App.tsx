import React from 'react';
import './App.css';
import Carousel from './components/Carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import styles from './index.module.scss';
import cx from 'classnames';

function App() {
  const carouselItems: React.ReactNode[] = [
    <div className={styles.banner} style={{ backgroundImage: `url(${iphone})` }}>
      <div className={cx(styles.bannerTextContainer, styles.blackBackground)}>
        <div className={styles.bannerTitle}>xPhone</div>
        <div className={styles.bannerDescText}>Lots to love.Less to spend.</div>
        <div className={styles.bannerDescText}>Starting at $399.</div>
      </div>
    </div>,
    <div className={styles.banner} style={{ backgroundImage: `url(${tablet})` }}>
      <div className={styles.bannerTextContainer}>
        <div className={styles.bannerTitle}>Tablet</div>
        <div className={styles.bannerDescText}>Just the right amount of everything.</div>
      </div>
    </div>,
    <div className={styles.banner} style={{ backgroundImage: `url(${airpods})` }}>
      <div className={styles.bannerTextContainer}>
        <div className={styles.bannerTitle}>Byu a Tablet or xPhone for college.</div>
        <div className={styles.bannerTitle}>Get airPods.</div>
      </div>
    </div>,
  ]

  return (
    <div className='App'>
      <Carousel
        autoPlay
        pauseDuringFocus
        items={carouselItems}
        style={{ height: 600 }}
      />
    </div>
  );
}

export default App;
