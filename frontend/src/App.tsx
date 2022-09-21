
import "./App.scss";
import "./carousel.scss";
import React, { useContext, createContext, useEffect, useReducer } from 'react';

const UPDATE_CONTENT = 'UPDATE_CONTENT'

type StateType = {
  page: number
}

type ActionType = {
  type: string,
  page: number
}

type MixStateAndDispatch = {
  state: StateType,
  dispatch?: React.Dispatch<ActionType>
}

const CarouselContext = React.createContext<MixStateAndDispatch>({
  state: { page: 1 }
});

const reducer = (state: StateType, action: ActionType) => {
  switch(action.type) {
    case UPDATE_CONTENT:
      // console.log("action>>>", action)
      return { page: action.page }
    default:
      return state  
  }
}

const CarouselContent = () => {
  const { state, dispatch } = useContext<MixStateAndDispatch>(CarouselContext);

  const transaction = (state.page - 1) * 100;

  let transStyle: any = {
    flex: 1,
    backgroundColor: '#cccc99',
    width:'100%',
    height:'100%',
    transform: [
        { perspective: 500 },
        { translateX: `-${transaction}%` },
    ],
  }

  return (
    <div className="content">
      <div className="transform-container" style={transStyle}>
        <section className="fragment">
          页面1{state.page}
        </section>
        <section className="fragment">
          页面2{state.page}
        </section>
        <section className="fragment">
          页面3{state.page}
        </section>
      </div>
    </div>
  )
}

const CarouselBanner = ({ page = 1 }) => {
  const [state, dispatch] = useReducer(reducer, { page })

  let timer: number = 0;
  let curPage = 1;

  let frontStyle = {
    backgroundColor: '#ffdddd',
    width: timer * 10
  };

  let timerInterval: NodeJS.Timeout|null = setInterval(() => {
    timer++;
    if (timer > 100 && timer < 200) {
      curPage = 2;
    } else if (timer > 100 && timer < 200) {
      curPage = 3;
    } else {
      curPage = 1;
      timer = 0;
    }
    dispatch({type: UPDATE_CONTENT, page: curPage});
  }, 100);

  useEffect(()=> {
    return () => {
      window.clearInterval(timerInterval!);
      timerInterval = null;
    }
  });

  return (
    <div className="banner">
        {/* <button onClick={() => dispatch && dispatch({type: UPDATE_CONTENT, page: 1})}>第一页</button>
        <button onClick={() => dispatch && dispatch({type: UPDATE_CONTENT, page: 2})}>第二页</button>
        <button onClick={() => dispatch && dispatch({type: UPDATE_CONTENT, page: 3})}>第三页</button> */}
        <div className="bar">
          <div className={[state.page === 1 ? 'active' : null].join(' ')} style={frontStyle}></div>
        </div>
        <div className="bar">
          <div className={[state.page === 2 ? 'active' : null].join(' ')}  style={frontStyle}></div>
        </div>
        <div className="bar">
          <div className={[state.page === 3 ? 'active' : null].join(' ')}  style={frontStyle}></div>
        </div>
    </div>
  )
}

const Carousel =({ page = 1 }) => {
  const [state, dispatch] = useReducer(reducer, { page })

  return (
      <div className="Carousel">
        <CarouselContext.Provider value={{state, dispatch}}>
          <CarouselContent />
          <CarouselBanner />
        </CarouselContext.Provider>
      </div>
  )
}

function App() {
  return <div className="App">
      <Carousel />
  </div>;
}

export default App;
