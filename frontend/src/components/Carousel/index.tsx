import React, { FC } from "react"
import CarouselBase, { Props as CarouselBaseProps } from "./CarouselBase"
import Board, { Props as BoardProps } from "./Board"

interface IData extends BoardProps {
  id: string
}

interface IProps {
  dataList: IData[]
}

export type Props = IProps & Pick<CarouselBaseProps, "duration">

const Carousel: FC<Props> = (props) => {
  const { dataList, ...rest } = props
  return (
    <CarouselBase {...rest}>
      {dataList.map((item) => (
        <Board key={item.id} {...item} />
      ))}
    </CarouselBase>
  )
}

export default Carousel
