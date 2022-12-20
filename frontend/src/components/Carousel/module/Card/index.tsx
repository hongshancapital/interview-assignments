import { useState } from 'react'
import style from './index.module.scss'
import iphone from '@/assets/iphone.png'

import { getImgColor } from '@/utils'

export default function Card({ title, description, img }: any) {
  const [dark, setDark] = useState<boolean>(true)
  return (
    <div className={style.card}>
      <img
        src={img}
        alt={title}
        onLoad={(e) => setDark(getImgColor(e.target as HTMLImageElement))}
        onError={(e) => ((e.target as HTMLImageElement).src = iphone)}
      />
      <div className={style.content} style={{ color: !dark ? '#fff' : '#000' }}>
        <h3 className="title">
          <b>{title}</b>
        </h3>
        <p className="text">{description}</p>
      </div>
    </div>
  )
}
