/*
 * @Author: danjp
 * @Date: 2022/6/24 0:06
 * @LastEditTime: 2022/6/24 0:06
 * @LastEditors: danjp
 * @Description:
 */
import React from 'react';
import classNames from 'classnames';
import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';
import airpodsImg from './assets/airpods.png';
import styles from './Page.module.scss';

export const PhonePage = () => (
  <div className={classNames(styles.container, styles.container__phone)}>
    <img src={iphoneImg} className={styles.img} alt='xPhone' />
    <div className={styles.content}>
      <div className={styles.title}>xPhone</div>
      <div className={styles.desc}>
        Lots to love.Less to speed.
        Starting at $39
      </div>
    </div>
  </div>
);

export const TabletPage = () => (
  <div className={classNames(styles.container, styles.container__tablet)}>
    <img src={tabletImg} className={styles.img} alt='Tablet' />
    <div className={styles.content}>
      <div className={styles.title}>Tablet</div>
      <div className={styles.desc}>
        Just the right amount of everything.
      </div>
    </div>
  </div>
);

export const AirpodsPage = () => (
  <div className={classNames(styles.container, styles.container__airpods)}>
    <img src={airpodsImg} className={styles.img} alt='Airpods' />
    <div className={styles.content}>
      <div className={styles.title}>
        Buy a Tablet or xPhone for college.
        Get arPods.
      </div>
    </div>
  </div>
);

interface PageDataItem {
  id: number;
  img: string;
  title: string;
  desc?: string;
}

const PageDataList: PageDataItem[] = [
  { id: 1, img: iphoneImg, title: 'xPhone', desc: 'Lots to love.Less to speed.Starting at $39' },
  { id: 2, img: tabletImg, title: 'Tablet', desc: 'Just the right amount of everything.' },
  { id: 3, img: airpodsImg, title: 'Tablet', desc: 'Buy a Tablet or xPhone for college.Get arPods.' },
];

export const PageList = () => (
  <div className={styles.container}>
    {PageDataList.map(item => (
      <React.Fragment key={item.id}>
        <img src={item.img} className={styles.img} />
        <div className={styles.title}>{item.title}</div>
        {item.desc && (
          <div className={styles.desc}>
            {item.desc}
          </div>
        )}
      </React.Fragment>
    ))}
  </div>
);
