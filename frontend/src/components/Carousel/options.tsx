import React from 'react'
import styles from './index.module.css'

import airpods from '../../assets/airpods.png'
import iphone from '../../assets/iphone.png'
import tablet from '../../assets/tablet.png'

/**
 * 每一项展示时间
 */
export const SHOW_TIME = 4000

/**
 * 切换时过渡时间
 */
export const TRANSITION_TIME = 400

export const assets = [
  {
    id: 1,
    backgroundColor: '#F1F1F1',
    children: (
      <>
        <div className={styles.ads}>
          <h1 className={styles.title}>Buy a Tablet or XPhone for collerge.</h1>
          <h1 className={styles.title}>Get arPords - airpods</h1>
        </div>
        <img className={styles.bg} src={airpods} alt="airpods" />
      </>
    )
  },
  {
    id: 2,
    backgroundColor: '#111111',
    children: (
      <>
        <div className={styles.ads}>
          <h1 className={`${styles.title} ${styles.white}`}>Get arPords - iphone</h1>
        </div>
        <img className={styles.bg} src={iphone} alt="iphone" />
      </>
    )
  },
  {
    id: 3,
    backgroundColor: '#FAFAFA',
    children: (
      <>
        <div className={styles.ads}>
          <h1 className={styles.title}>Get arPords - tablet</h1>
        </div>
        <img className={styles.bg} src={tablet} alt="tablet" />
      </>
    )
  },
]