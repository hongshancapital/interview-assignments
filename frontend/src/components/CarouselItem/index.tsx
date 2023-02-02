import { CSSProperties, FC, ReactNode } from 'react'
import styles from './index.module.scss'

interface CarouselItemProps {
  img: string
  style?: CSSProperties
  children: ReactNode
}

const CarouselItem: FC<CarouselItemProps> = ({ img, style, children }) => {
  return (
    <div className={styles.item} style={style}>
      <img className={styles.background} src={img} alt='carousel img' />
      <div className={styles.content}>{children}</div>
    </div>
  )
}

export default CarouselItem
