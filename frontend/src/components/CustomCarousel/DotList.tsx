import React from 'react'

import styles from './DotList.module.css'

interface IProps {
  activeIndex: number;
  len: number;
}

export default function (props: IProps) {
  return (
    <ul className={styles.dotList}>
      {
        new Array(props.len).fill(0).map((_, idx) => (
          <li
            data-testid="item"
            key={idx} 
            className={`${styles.dotItem} ${props.activeIndex === idx ? styles.active : '' }`}
          >
            <span className={styles.dotFront}></span>
          </li>
        ))
      }
    </ul>
  )
}