import classNames from 'classnames'
import React, { HTMLAttributes, useState, useEffect } from 'react'

export interface IDotsProps extends HTMLAttributes<HTMLLIElement> {
  count: number
  current: number
  autoPlayInterval?: number
}

export default function Dots(props: IDotsProps) {
  const { count, current, autoPlayInterval, ...restProps } = props
  const [firstRender, setFirstRender] = useState<boolean>(false)
  useEffect(() => {
    if (!firstRender) {
      setFirstRender(true)
    }
  }, [firstRender])
  const innerStyle = (index: number) => ({
    transitionDuration:
      index === current ? `${props.autoPlayInterval}ms` : '0ms',
  })
  return (
    <ul className="com-dots">
      {new Array(count).fill(1).map((_, index) => {
        return (
          <li key={index} {...restProps} className="com-dots-item">
            <div
              className={classNames('com-dots-inner', {
                'com-dots-actived':
                  !!autoPlayInterval && firstRender && index === current,
              })}
              style={innerStyle(index)}
            />
          </li>
        )
      })}
    </ul>
  )
}
