import React, {
  useEffect,
  useLayoutEffect,
  useState,
  useRef,
  useReducer,
  useCallback,
  ReactNode,
  ReactFragment
} from 'react';
import ReactDOM from 'react-dom';

import worker_script from './worker';

const Timer = (time: any) => {
  const timer = useRef<any>(null);
  const t = useRef(time);
  const [second, setSecond] = useState<any>('');
  const [minute, setMinute] = useState<any>('');
  const [hour, setHour] = useState<any>('');

  const changeTime = () => {
    const seconds = t.current;
    let hour = Math.floor(seconds / 3600);
    const showHour = hour > 10 ? hour : `0${hour}`;
    let minute = Math.floor((seconds - hour * 3600) / 60);
    const showMinute = minute > 10 ? minute : `0${minute}`;
    const second = seconds - hour * 3600 - minute * 60;
    const showSecond = second > 10 ? second : `0${second}`;
    console.log(hour, minute, second);
    setSecond(showSecond);
    setMinute(showMinute);
    setHour(showHour);
  };

  useEffect(() => {
    t.current = time;
    timer.current && clearInterval(timer.current);

    changeTime();
    timer.current = setInterval(() => {
      t.current--;
      changeTime();
    }, 1000);

    return () => {
      clearInterval(timer.current);
    };
  }, [time]);

  return {
    second,
    minute,
    hour
  };
};

const Example = () => {
  const [count, setCount] = useState(0);

  useEffect(() => {
    console.log('哈哈哈，useEffect 又执行了');
    return () => {
      console.log('看到我就知道执行了清除机制(～￣▽￣)～');
    };
  }, [count]);

  useLayoutEffect(() => {
    console.log('useLayoutEffect');
  }, [count]);

  return (
    <div>
      <p>那啥，你点了我 {count} 次 ⏲️⏲️⏲️⏲️</p>
      {console.log('这是 dom 节点渲染了，小样╭(╯^╰)╮')}
      <button
        onClick={() => {
          setCount(count + 1);
        }}
      >
        你觉得你点击我之后会发生什么⛏️⛏️⛏️
      </button>
    </div>
  );
};

function reducer(state: any, action: any) {
  switch (action.type) {
    case 'increment':
      return { count: state.count + 1 };
    case 'decrement':
      return { count: state.count - 1 };
    default:
      throw new Error();
  }
}

const Dialog = ({ children, onClose, visible }: any) => {
  return (
    <div>
      {children}
      <div>
        <button onClick={onClose}>close</button>
      </div>
    </div>
  );
};

const modal = (content: ReactNode | ReactFragment) => {
  const div = document.createElement('div');
  const onClose = () => {
    ReactDOM.render(React.cloneElement(component, { visible: false }), div);
    ReactDOM.unmountComponentAtNode(div);
    div.remove();
  };
  const component = (
    <Dialog onClose={onClose} visible={true}>
      {content}
    </Dialog>
  );
  document.body.appendChild(div);
  ReactDOM.render(component, div);
  return onClose;
};

let worker: any;

function App1() {
  const [state, dispatch] = useReducer(reducer, {
    count: 1
  });
  const openModal = () => {
    const close = modal(
      <h1>
        你好
        <button onClick={() => close()}>close</button>
      </h1>
    );
  };
  useEffect(() => {
    worker = new Worker(worker_script);
    worker.onmessage = function (event: any) {
      console.log(`Received message ${event.data}`);
    };
  }, []);
  const handleChange = (e: any) => {
    worker.postMessage(e.target.value);
  };
  const [count, setCount] = useState(1);
  // useEffect(() => {
  //   setInterval(() => {
  //     console.log(count);
  //   }, 1000);
  // }, []);
  const changeCount = () => setCount(count => count + 1);

  return (
    <>
      <button onClick={changeCount}>{count}</button>
      <input type='text' id='input1' onChange={handleChange} />
      <button onClick={openModal}>modal</button>
      Count: {state.count}
      <button onClick={() => dispatch({ type: 'decrement' })}>-</button>
      <button onClick={() => dispatch({ type: 'increment' })}>+</button>
    </>
  );
}

const Child = React.memo(({ onClick }: any) => {
  console.log(`Button render`);
  return (
    <div>
      <button onClick={onClick}>child button</button>
    </div>
  );
});

function App() {
  const [countA, setCountA] = useState(0);
  const [countB, setCountB] = useState(0);

  // const onClick = () => {
  //   console.log(111);
  // }

  const onClick = useCallback(() => {
    console.log(111);
  }, [countA]);

  return (
    <div className='App'>
      <div>countB:{countB}</div>
      <button onClick={() => setCountB(countB + 1)}>App button</button>

      <Child onClick={onClick} />
      <button></button>
      {/* <Example /> */}
    </div>
  );
}

export default App;
