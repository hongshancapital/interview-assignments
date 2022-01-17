import React,{FunctionComponent} from 'react'
import {TitleFont,TextFont,Img,Wrapper} from '../../styles'

type Props = {
  title:string;
  subTitle?:string;
  img:string;
  theme?:string|'dark'|'light';
}

const App:FunctionComponent<Props> = (props) => {
  return (
    <Wrapper {...props}>
      <TitleFont>{props.title}</TitleFont>
      <TextFont>{props.subTitle}</TextFont>
      <Img src = {props.img}/>
    </Wrapper>
  )
}

export default App
