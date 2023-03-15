import React, { FC } from "react"
import CarouselBase, { Props as CarouselBaseProps } from "./CarouselBase"
import Board, { Props as BoardProps } from "./Board"
import { createPropsGetter } from "../../utils/createPropsGetter"

interface IData extends BoardProps {
  id: string
}

interface IProps {
  dataList: IData[]
}

const defaultProps = {}

type DefaultProps = Readonly<typeof defaultProps>

export type Props = IProps &
  Partial<DefaultProps> &
  Pick<CarouselBaseProps, "duration">

const getProps = createPropsGetter<DefaultProps>()

const Carousel: FC<Props> = (props) => {
  const { dataList, ...rest } = getProps(props)
  return (
    <CarouselBase {...rest}>
      {dataList.map((item) => (
        <Board key={item.id} {...item} />
      ))}
    </CarouselBase>
  )
}

Carousel.defaultProps = defaultProps

export default Carousel
