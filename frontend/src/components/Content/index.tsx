import React from "react";
import classnames from 'classnames'
import styles from './index.module.scss'

export interface IProps {
  backgroundColor: string
  color: string
  img: string
  title: string | string[]
  subtitle: string | string[]
}

export function Content(props: IProps) {
  const {
    backgroundColor,
    color,
    img,
    title,
    subtitle,
  } = props
  const _subtitle = Array.isArray(subtitle) ? subtitle : subtitle ? [] : [subtitle]
  const _title = Array.isArray(title) ? title : [title]
  return (
    <div style={{ backgroundColor }} className={classnames({ [styles.content]: true, [styles.iphone]: true })}>
      <div className={styles.context} style={{ color }}>
        {
          _title.length ? _title.map((t) => <h1 key={t}>{t}</h1>) : null
        }
        {_subtitle.length ? (
          <p>
            {_subtitle.map((sub) => <span key={sub}>{sub}</span>)}
          </p>
        ) : null}
      </div>

      <img className={styles.img} alt="img" src={img} />
    </div>
  );
}