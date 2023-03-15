import React, { FC } from "react"
import "./index.scss"

interface IProps {}

const defaultProps = {}

type DefaultProps = Readonly<typeof defaultProps>

export type Props = IProps & Partial<DefaultProps>

const Carousel: FC<Props> = (props) => {
  return <div className="carousel">Carousel</div>
}

Carousel.defaultProps = defaultProps

export default Carousel
