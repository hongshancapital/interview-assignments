import React, { CSSProperties, FC, ReactNode } from "react"
import { compose, curry, map } from "ramda"

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

export interface Props {
  title: string | string[]
  description?: string | string[]
  style?: Partial<IStyle>
  backgroundImage?: string
}

const formatText = (text: string | string[]) => {
  if (typeof text === "string") return [text]

  if (Array.isArray(text)) return text

  return []
}

const renderDescriptionItem = curry<
  (type: TextTypeEnum, text: string) => ReactNode
>((type, text) => {
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
})

const Board: FC<Props> = (props) => {
  const { title, description = "", style = {}, backgroundImage = "" } = props

  const renderTitles = compose(
    map<string, ReactNode>(renderDescriptionItem(TextTypeEnum.Title)),
    formatText
  )

  const renderDescriptions = compose(
    map<string, ReactNode>(renderDescriptionItem(TextTypeEnum.Description)),
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

export default Board
