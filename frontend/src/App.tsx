import styled from '@emotion/styled'
import Carousel from './components/Carousel'
import file from '../src/assets/file.png'

const Content = styled.div`
  position: fixed;
  width: 100%;
  height: 50%;
`

const Box = styled.div<{ bColor?: string }>`
  width: 100%;
  height: 100%;
  background-color: ${(props) => props.bColor || 'white'};
  padding: 40px 0;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
`

const Title = styled.div<{ color?: string; size?: string }>`
  font-size: ${(props) => props.size || '14px'};
  color: ${(props) => props.color || 'black'};
  width: 100%;
  text-align: center;
`

const Image = styled.img`
  width: 100px;
  height: 100px;
  background-size: 100% 100%;
  margin-top: 50px;
`

function App() {
  return (
    <Content>
      <Carousel>
        <Box bColor="black">
          <Title color="#fff" size="40px">
            Slide 1
          </Title>
          <Image src={file} alt="" />
        </Box>
        <Box>
          <Title size="40px">Slide 2</Title>
          <Image src={file} alt="" />
        </Box>
        <Box>
          <Title size="40px">Slide 3</Title>
          <Image src={file} alt="" />
        </Box>
      </Carousel>
    </Content>
  )
}

export default App
