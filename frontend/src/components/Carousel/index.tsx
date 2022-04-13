import React, {FC, useState, useRef, useEffect, CSSProperties} from 'react'
import styles from './style.module.scss';
import classnames from 'classnames'

interface Item {
  title: string;
  subTitle: string;
  imgUrl?: string;
  style?: CSSProperties;
}
interface IProps {
  products: Item[];
  delay: number;
}

interface IProgressProps extends IProps {
  curIdx: number;
}
export const Progress: FC<IProgressProps> = ({ products, delay, curIdx }) => {
  return (
    <div className={styles.progressWrapper} data-testid="Carousel">
      {
        products.map((item, index) => {
          return (
          <div className={styles.item} key={index} data-testid="CarouselProgressItem">
            <div className={curIdx === index ? styles.progress_active : styles.progress } style={{ animationDuration: `${delay}ms` }}></div>
          </div>)
        })
      }
    </div>
  )
}


const Carousel: FC<IProps> = ({products, delay}) => {
  const [index, setIndex] = useState<number>(0);
  const timeoutRef = useRef<NodeJS.Timeout>();
  function resetTimeout() {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }
  }
  useEffect(() => {
    resetTimeout()
    timeoutRef.current = setTimeout(() => {
      setIndex((preIndex: number) =>
        preIndex === products.length - 1 ? 0 : preIndex + 1
      )
    }, delay);
    return (() => {
      resetTimeout()
    })
  }, [index])

  return (
    <div className={styles.slideContainer}>
      <div
        className={styles[`slideshowSlider_${index}`]}
      >
        {
          products.map((product: Item, index) => {
            return (
              <div className={styles.slide} key={index} style={{ ...product.style }} data-testid="CarouselItem">
                  <div className={styles.descWrapper}>
                    <span className={styles.title} style={product.style}>{product.title}</span>
                    <span style={product.style} className={styles.subTitle}>{product.subTitle}</span>
                    <img src={product.imgUrl} height={"50%"} alt={product.title} />
                 </div>
              </div>
            )
          })
        }
      </div>
      <Progress products={products} curIdx={index} delay={delay} />
    </div>
  )
}

export default Carousel
