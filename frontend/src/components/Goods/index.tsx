import { CSSProperties } from 'react'
import './index.css'

interface IProps {
  picture: string
  title: string[]
  description: string[]
  wrapStyles: CSSProperties
}

export default function Goods({
  picture,
  title = [],
  description = [],
  wrapStyles
}: IProps) {
  return (
    <div className='Goods' style={{ ...wrapStyles, backgroundImage: `url(${picture})` }}>
      <div className='Goods__title'>
        {title.map(text => (
          <div className='Goods__titleItem' key={text}>{text}</div>
        ))}
      </div>
      <div className='Goods__desc'>
        {description.map(text => (
          <div className='Goods__descItem' key={text}>{text}</div>
        ))}
      </div>
    </div>
  )
}