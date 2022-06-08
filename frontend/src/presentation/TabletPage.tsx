import React from "react"
import styled from 'styled-components'

import tablet from '../assets/tablet.png'

const Container = styled.div`
  display: flex;
  justify-content: center;
  height: 100%;
  background: #fafafa;
`

const TitleArea = styled.div`
  position: absolute;
  top: 15%;
  left: 10%;
  width: 80%;
  text-align: center;
  color: #111111;
`

const Title = styled.h1`
  margin: 0 0 30px 0;
  font-size: 80px;
  font-weight: 600;
`

const SubTitle = styled.h2`
  margin: 0 0 5px 0;
  font-size: 30px;
  font-weight: 400;
  font-family: sans-serif;
`

export const TabletPage = () => {
  return (
    <Container>
      <TitleArea>
        <Title>Tablet</Title>
        <SubTitle>Just the right amount of everything.</SubTitle>
      </TitleArea>
      <img src={tablet} />
    </Container>
  )
}