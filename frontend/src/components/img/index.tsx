import { FC } from 'react'

type PropType = {
  src: string
  alt?: string
  [key: string]: any
}

const Img: FC<PropType> = ({ src, alt, ...res }) => {
  return <img src={src.includes('http') ? src : require(`@/assets${src}`)} alt={alt || '图片'} {...res} />
}

export default Img
