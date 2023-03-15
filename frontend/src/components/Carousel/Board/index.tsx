import React, { CSSProperties, FC, ReactNode } from "react"
import { compose, curry, map } from "ramda"

import { createPropsGetter } from "../../../utils/createPropsGetter"
import "./index.scss"

enum TextTypeEnum {
  Title,
  Description,
}

interface IStyle {
  "--width": string
  "--height": string
  "--text-color": string
  "--image-size": string
}

interface IProps {
  title: string | string[]
}

const defaultProps = {
  description: "" as string | string[],
  style: {} as Partial<IStyle>,
  backgroundImage: "",
}

type DefaultProps = Readonly<typeof defaultProps>

export type Props = IProps & Partial<DefaultProps>

const getProps = createPropsGetter<DefaultProps>()

const formatText = (text: DefaultProps["description"]) => {
  if (typeof text === "string") return [text]

  if (Array.isArray(text)) return text

  return []
}

const renderDescriptionItem = curry(
  (type: TextTypeEnum, text: string): ReactNode => {
    if (type === TextTypeEnum.Description)
      return (
        <p className="board__description" key={text}>
          {text}
        </p>
      )

    if (type === TextTypeEnum.Title)
      return (
        <p className="board__title" key={text}>
          {text}
        </p>
      )

    return ""
  }
) as (p1: TextTypeEnum) => (p2: string) => ReactNode

const Board: FC<Props> = (props) => {
  const { title, description, style, backgroundImage } = getProps(props)

  const renderTitles = compose(
    map(renderDescriptionItem(TextTypeEnum.Title)),
    formatText
  )

  const renderDescriptions = compose(
    map(renderDescriptionItem(TextTypeEnum.Description)),
    formatText
  )

  return (
    <div className="board" style={style as CSSProperties}>
      <div>{renderTitles(title)}</div>
      <div>{renderDescriptions(description)}</div>
      <img className="board__image" src={backgroundImage} />
    </div>
  )
}

Board.defaultProps = defaultProps

export default Board
