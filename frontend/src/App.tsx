import React, { useCallback } from "react";
import Carousel from "./components/Carousel";
import iphoneIcon from './assets/iphone.png';
import tabletIcon from './assets/tablet.png';
import airpodsIcon from './assets/airpods.png';

import styles from "./App.module.scss";


interface IBanner {
  id: number,
  title: string[],
  desc: string[],
  image: string,
  bgColor: string,
  fontColor: string

}

function App() {

  // demo 数据
  const data: IBanner[] = [
    {
      id: 1,
      title: ['xPhone'],
      desc: ['Lots to love.Less to spend.', 'Strating at $399.'],
      bgColor: '#111111',
      fontColor: 'white',
      image: iphoneIcon
    },
    {
      id: 2,
      title: ['Tablet'],
      desc: ['Just the right amount of everything.'],
      bgColor: '#fafafa',
      fontColor: '#000',
      image: tabletIcon
    },
    {
      id: 3,
      title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
      desc: [],
      bgColor: '#F1F1F3',
      fontColor: '#000',
      image: airpodsIcon
    },
  ]

  const onChange = useCallback((index) => {
    // console.log(`slide index :=> ${index}`)
  }, [])

  return <div className={styles.app} > {
    <Carousel interval={3000} onChange={onChange}>
      {
        data.map((item: IBanner) => {
          return <div key={item.id} className={styles.banner} style={{ backgroundImage: `url(${item.image})`, backgroundColor: item.bgColor }}>
            <div className={styles.banner_title} style={{ color: item.fontColor }} >
              {
                item.title && item.title.map((title, index) => {
                  return <p key={index}>{title}</p>
                })
              }
            </div>

            <div className={styles.banner_desc} style={{ color: item.fontColor }}>
              {
                item.desc && item.desc.map((desc, index) => {
                  return <p key={index}>{desc}</p>
                })
              }
            </div>
          </div>
        })
      }
    </Carousel>
  }</div >;
}

export default App;
