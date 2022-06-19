import React, { useState, useReducer, useEffect } from 'react';
import "./index.css"
type StateType = {
  selected: number,
  isFirst: Boolean
}
type ActionType = {
  type: String,
  payload: number
}
const pages = [{
  titles: ['xPhone'],
  descriptions: ['Lots to love.Less to spend.', 'Starting at $399'],
  img: require('../assets/iphone.png')
}, {
  titles: ['Tablet'],
  descriptions: ['Just the right amount of everything'],
  img: require('../assets/tablet.png')
}, {
  titles: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
  descriptions: ['Just the right amount of everything'],
  img: require('../assets/tablet.png')
}];
const initialState = {
  selected: 0,
  isFirst: true
}
function reducer(state: StateType, action: ActionType) {
  // debugger
  switch(action.type) {
    case 'increase':
      return {
        ...state,
        selected: (state.selected + 1) % 3
      }
    case 'first': 
      return {
        ...state,
        isFirst: false
      }
    default:
      return state;
  }
}

function PageItem(props:any) {
  const className = 'page-item page-item-' + props.index;
  const page = props.page;
  return <div className={className} style={{backgroundImage: `url(${page.img})`, width: props.width + 'px'}}>
    <div className="empty"></div>
    {
      page.titles.map((title:string, index:number) => <div key={'title-' + index} className="title">{title}</div>)
    }
    {
      page.descriptions.map((des:string, index:number) => <div key={'des-' + index} className="text">{des}</div>)
    }
  </div>
}
function MyPage(props: any) {
  const [width, setWidth] = useState(window.innerWidth);
  window.addEventListener('resize', () => {
    setWidth(window.innerWidth)
  });
  let selected = props.selected;
  if(selected === -1) selected = 0;
  return <div className='page-content' style={{width: width * pages.length + 'px', transform: `translatex(${-width * selected}px)`}}>
    {
      pages.map((item, index) => <PageItem width={width}  key={'page-' + index} selected={selected} index={index} page={item} />)
    }
    </div>
}
function NavigatorItem(props: any) {
  const index = props.index;
  const [myState, myDispatch] = useReducer(reducer, initialState);
  let canSelected = index === props.selected;
  if(myState.isFirst && index === 0 && canSelected) canSelected = false;
  // if(myState.isFirst && index === 0) {
  //   myDispatch({
  //     type: 'first',
  //     payload: 0
  //   })
  // }
  useEffect(() => {
    if(!myState.isFirst || index  !== 0) return;
    const timer = setTimeout(() => {
      console.log('call gonext setTimeout,', new Date().getTime())
      myDispatch({
        type: 'first',
        payload: 0
      });
    }, 0);
    return () => clearTimeout(timer);
  })

  return <div className="item">
    <div className={canSelected ? 'selected' : ''}></div>
  </div>
}
function MyNavigator(props: any) {
  return <div className='navigator'>
    <div className='navigator-container'>
      { 
        pages.map((_, index) => {
          return <NavigatorItem key={`item-${index}`} index={index} selected={props.selected} />
        })
      }
    </div>
  </div>
}
function Carousel() {
  const [myState, myDispatch] = useReducer(reducer, initialState);
  console.log('call Carousel:', myState.selected)
  useEffect(() => {
    const timer = setTimeout(() => {
      myDispatch({
        type: 'increase',
        payload: 1
      })
    }, 3000);
    return () => clearTimeout(timer);
  })
  return <div className='page-container'>
    <MyPage selected={myState.selected} />
    <MyNavigator selected={myState.selected} />
  </div>;
}
export default Carousel;
