import classnames from 'classnames'
import React, { HTMLAttributes } from 'react'
import './index.scss'

export interface ICarouselItemProps extends HTMLAttributes<HTMLDivElement> {
  key: string
  header?: string[]
  content?: string[]
  imageSrc?: string
  theme?: 'dark' | 'light' | 'gray'
}
const classPrefix = 'prefix-carousel-item'
export default function CarouselItem(props: ICarouselItemProps) {
  const { key, header, content, imageSrc, theme, className, ...restProps } =
    props
  return (
    <div
      className={classnames(
        className,
        `${classPrefix}-wrap`,
        `${classPrefix}-${theme}`,
      )}
      {...restProps}
    >
      <img className={`${classPrefix}-image`} src={imageSrc} alt={key} />
      <div className={classnames(`${classPrefix}`)}>
        {!!header?.length &&
          header.map((title: string, index: number) => (
            <h2 className={`${classPrefix}-header`} key={index}>
              {title}
            </h2>
          ))}
        {!!content?.length &&
          content.map((text: string, index: number) => (
            <p className={`${classPrefix}-content`} key={index}>
              {text}
            </p>
          ))}
      </div>
    </div>
  )
}
