import React, { FC, useEffect, useState } from 'react'
// import classnames from 'classnames'
import './index.scss'

interface IProps {
}

const defaultProps = {
}

type DefaultProps = Readonly<typeof defaultProps>

type Props = IProps & Partial<DefaultProps>

const Carousel: FC<Props> = (props) => {
  const { } = props;

  return (
    <div>
      123
    </div>
  )
}

Carousel.defaultProps = defaultProps;

export default Carousel
