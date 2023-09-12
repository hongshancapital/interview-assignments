import Carousel from "./components/Carousel"
import styled from "./app.module.scss"

function App() {
  return (
    <Carousel autoPlay>
      <div className={`${styled.item} ${styled.iphone}`}>
        <div className={styled.title}>xPhone</div>
        <div className={styled.desc}>Lots to love.Less to spend.</div>
        <div className={styled.desc}>Staring at $399.</div>
      </div>
      <div className={`${styled.item} ${styled.tablet}`}>
        <div className={styled.title}>Tablet</div>
        <div className={styled.desc}>Just the right amount of everything.</div>
      </div>
      <div className={`${styled.item} ${styled.aripods}`}>
        <div className={styled.title}>Buy a Tablet or Xphone for collage.</div>
        <div className={styled.title}>Get arPods.</div>
      </div>
    </Carousel>
  )
}

export default App
