import cn from 'classnames';
import { ItemRendererProps } from '../../types';
import styles from './index.module.scss';

/**
 * 元素渲染器
 * @param ItemRendererProps
 * @returns 
 */
function ItemRenderer({
  title,
  descriptions,
  image,
  theme,
  style,
}: ItemRendererProps) {

  return (
    <div style={style} className={cn(styles.ItemRenderer, styles[theme])}>
      <h1>{title}</h1>
      {
        descriptions && descriptions.map(
          (description, idx) => {
            return (
              <div key={idx.toString()}>{description}</div>
            )
          }
        )
      }
      <div className={styles.Space} />
      <img src={image} alt='img' />
    </div>
  )
}

export default ItemRenderer;