import { FC, ImgHTMLAttributes } from 'react'

const Img: FC<ImgHTMLAttributes<HTMLImageElement>> = ({ src, alt, ...res }) => {
  return (
    <img
      src={(src as string).includes('http') ? src : require(`@/assets${src}`)}
      alt={(alt as string) || '图片'}
      {...res}
    />
  )
}

export default Img
