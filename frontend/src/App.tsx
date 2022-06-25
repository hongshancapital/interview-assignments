import React from 'react';
import Carousel, { CarouselRef } from './Carousel/Carousel';
import { PageDataList } from './PageData';
import styles from './Page.module.scss';
import './App.css';

function App() {
  return (
    <div className='App'>
      <Carousel>
        {PageDataList.map((item) => (
          <div key={item.id} className={styles.container} style={item.style}>
            <img src={item.img} className={styles.img} alt={item.title} />
            <div className={styles.content}>
              <div className={styles.title} dangerouslySetInnerHTML={{ __html: item.title }}/>
              {item.desc && <div className={styles.desc} dangerouslySetInnerHTML={{ __html: item.desc }} />}
            </div>
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
