import React from 'react'

import CustomCarousel from '../components/CustomCarousel'

import styles from './index.module.css'

export default function Carousel () {
  return (
    <CustomCarousel>
      {/* 这里可以用一个配置项进行遍历，但是给的图片大小不一致，为了样式跟视频一致，暂且分列出来写 */}
      <div className={`${styles.item} ${styles.item1}`}>
        <h3 className={styles.title}>xPhone</h3>
        <p className={styles.text}>Lots to love. Less to spend.</p>
        <p className={styles.text}>Starting at $399.</p>
      </div>

      <div className={`${styles.item} ${styles.item2}`}>
        <h3 className={styles.title}>Tablet</h3>
        <p className={styles.text}>Just the right amount of everthing.</p>
      </div>

      <div className={`${styles.item} ${styles.item3}`}>
        <p className={styles.bigText}>Buy a Tablet or xPhone for college.</p>
        <p className={styles.bigText}>Get arPods.</p>
      </div>
    </CustomCarousel>
  )
}
