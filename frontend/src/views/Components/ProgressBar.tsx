import React,{FunctionComponent} from 'react'
import {Container} from '../../styles'
import styled from 'styled-components'
const Wrapper = styled(Container)`
  background:#9a9899;
  height:2px;
  border-radius:6px;
  width:50px;
  margin:5px;
  position:relative;
`
const Current = styled(Container)`
  background: #fff;
  height: 2px;
  border-radius: 6px;
  width: 0%;
`
type Props = {
  percent:number;
}

const App:FunctionComponent<Props> = (props) => {
  return (
    <Wrapper {...props}>
      <Current style={{width:`${props.percent}%`}} />
    </Wrapper>
  )
}

export default App
