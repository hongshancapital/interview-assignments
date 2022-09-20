import React from "react";
import Carousel, { ListItem } from '../components/Carousel'
import styles from "./AutoCarouselPage.module.css";
import iphone from '../assets/iphone.png';
import tabletImg from '../assets/tablet.png';
import airpodsImg from '../assets/airpods.png';

const listData: ListItem[] = [
  {
    url: iphone,
    title: 'xPhone',
    subTitle: '',
    text: 'Lots to love.Less to spend.',
    des: 'Starting at $339.',
    color: '#fff',
    key: 1,
  },
  {
    url: tabletImg,
    title: 'Tablet',
    text: 'Just the right amount of everything.',
    color: '#000',
    key: 2,
  },
  {
    url: airpodsImg,
    title: 'Buy a Tablet or xPhone for college',
    subTitle: 'Get arpods.',
    color: '#000',
    key: 3,
  },
]
function AutoCarouselPage () {
  return (
    <Carousel>
      {
        listData.map((item: ListItem) => (
          <RenderItem item={item} key={item.key} />
        ))
      }
    </Carousel>
  );
}

function RenderItem ({ item }: { item: ListItem }) {
  return (
    <div className={styles.itemBody} >
      <div
        className={styles.con}
        style={{color: item.color || '#000'}}
      >
        {
          item.title && <p className={styles.title}>{ item.title }</p>
        }
        {
          item.subTitle && <p className={styles.title}>{ item.subTitle }</p>
        }
        {
          item.text && <p className={styles.text}>{ item.text }</p>
        }
        {
          item.des && <p className={styles.text}>{ item.des }</p>
        }
      </div>
      <img src={item.url} alt={item.title} />
    </div>
  )
}

export default AutoCarouselPage;