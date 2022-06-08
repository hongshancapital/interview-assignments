import React from "react"
import styled from 'styled-components'

import phone from '../assets/iphone.png'

const Container = styled.div`
  display: flex;
  justify-content: center;
  height: 100%;
  background: #111111;
`

const TitleArea = styled.div`
  position: absolute;
  top: 15%;
  left: 10%;
  width: 80%;
  text-align: center;
  color: #ffffff;
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

export const PhonePage = () => {
  return (
    <Container>
      <TitleArea>
        <Title>xPhone</Title>
        <SubTitle>Lots to love. Less to spend.</SubTitle>
        <SubTitle>Starting at $399.</SubTitle>
      </TitleArea>
      <img src={phone} />
    </Container>
  )
}
