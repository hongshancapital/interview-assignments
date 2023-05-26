import './App.css';
import {Carousel} from "./components";
import {CarouselDirection} from "./components/Carousel/type";
import contents from "./content";

function App() {
  return <div className='App'>
    <Carousel
      direction={CarouselDirection.Horizontal}
      data={contents}
      firstItem={1}
      keyExtractor={(item, index) => index}
      renderItem={(({item: Item}) => (
        <Item />
      ))}
    />
  </div>;
}

export default App;
