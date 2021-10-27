import React from 'react';

import styles from './App.module.scss';
import Carousel, { CarouselOption } from './components/Carousel';

import IPHONE_IMG from './assets/iphone.png';
import TABLE_IMG from './assets/tablet.png';
import AIRPODS_IMG from './assets/airpods.png';

/** 转换图片资源 */
const url = (src: string): string => `url(${src})`;

/** 轮播图展示配置 */
const CAROUSEL_OPTIONS: CarouselOption[] = [
  {
    title: 'xPhone',
    content: (
      <>
        <div>Lots to love. Less to spend.</div>
        <div>Starting at $399.</div>
      </>
    ),
    style: { color: 'white', backgroundImage: url(IPHONE_IMG), backgroundColor: '#111111' },
  },
  {
    title: 'Tablet',
    content: 'Just the right amount of everything.',
    style: { backgroundImage: url(TABLE_IMG), backgroundColor: '#fafafa' },
  },
  {
    title: (
      <>
        <div>Buy a Tablet or xPhone for college.</div>
        <div>Get arPods.</div>
      </>
    ),
    style: { backgroundImage: url(AIRPODS_IMG), backgroundColor: '#f1f1f3' },
  },
];

function App() {
  return (
    <div className={styles.app}>
      <Carousel className={styles['main-carousel']} options={CAROUSEL_OPTIONS} delay={2500} />
    </div>
  );
}

export default App;
