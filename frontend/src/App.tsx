import React from 'react'
import styled from 'styled-components'
import Carousel from './components/Carousel/index'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import './App.css'

const ITEMS = [
  {
    image: iphone,
    header: ['xPhone'],
    text: ['Lots to Love. Less to spend.', 'Starting at $399.'],
    textColor: 'white'
  },
  {
    image: tablet,
    header: ['Tablet'],
    text: ['Just the right amount of everything.']
  },
  {
    image: airpods,
    header: ['Buy a Tablet or xPhone for college.', 'Get arPods.']
  }
]

const Item = styled.div<{ image: string }>`
  width: 100%;
  height: 100%;
  background: url('${props => props.image}') bottom / cover no-repeat;
`
const Content = styled.div`
  margin-top: 170px;
  text-align: center;
`
// https://blog.agney.dev/styled-components-&-typescript/
const Text = styled.header<{ textColor?: string }>`
  color: ${props => props.textColor || 'black'};
  font-size: 24px;
`
// https://styled-components.com/docs/basics#extending-styles
const Header = styled(Text)`
  margin-bottom: 10px;
  font-size: 48px;
  font-weight: 500;
`

function App() {
  return (
    <div className="App">
      <Carousel width="100%" height="100%">
        {ITEMS.map(({ image, header, text, textColor }) => (
          <Item image={image} key={image}>
            <Content>
              {header?.map(h => (
                <Header textColor={textColor} key={h}>
                  {h}
                </Header>
              ))}
              {text?.map(t => (
                <Text textColor={textColor} key={t}>
                  {t}
                </Text>
              ))}
            </Content>
          </Item>
        ))}
      </Carousel>
    </div>
  )
}

export default App
