import React from 'react'
import cls from 'classnames'

import { prefixCls } from './constants'
import { IDotProps, IDotItemProps } from './types'

import './index.css'

const dotPrefixCls = `${prefixCls}__dot`

const DotItem = ({ duration, isActive }: IDotItemProps) => {
  const dotLineCls = cls(`${dotPrefixCls}__item__line`, {
    [`${dotPrefixCls}__item__line--active`]: isActive
  })

  return (
    <span className={`${dotPrefixCls}__item`}>
      <span
        className={dotLineCls}
        style={{
          transition: isActive ? `width linear ${duration / 1000}s` : undefined
        }}
      />
    </span>
  )
}

export default function Dots({ duration, currIndex, count }: IDotProps) {
  return (
    <div className={dotPrefixCls}>
      {Array(count)
        .fill('')
        .map((item, index) => (
          <DotItem
            key={index}
            duration={duration}
            isActive={index === currIndex}
          />
        ))}
    </div>
  )
}
