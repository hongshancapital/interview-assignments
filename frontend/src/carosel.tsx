import React , {useState,useEffect,useRef} from 'react';
import "./carosel.css";

type CarouselProps = {
  children?: React.ReactNode;
  autoplay?: Boolean; 
};

type CarouselItemProps = {
  children?: React.ReactNode; 
  style?:React.CSSProperties;
  className?: string;
};

function useSize(){
  const ref = useRef<any>(null);
  const [state, setState] = useState(function(){
    return {
      width:0,
      height:0
    }
  });
  React.useEffect(()=>{
    const targetEle = ref.current;
    if (!targetEle) {
        return function() {};
    };
    const resizeObserver = new ResizeObserver(function(entries){
      if(entries[0].target.clientWidth !== state.width || entries[0].target.clientHeight !== state.height){
        setState({
          width:entries[0].target.clientWidth,
          height:entries[0].target.clientHeight
        });
      }
      
    })
    resizeObserver.observe(targetEle);

    return ()=>{
      resizeObserver.disconnect();
    }
  },[ref.current]);
  return  {state, ref };
}



function useInterval(callback:Function, delay:number) {
  const savedCallback = useRef<Function>();
  useEffect(() => {
    savedCallback.current = callback;
  });

  useEffect(() => {
    function tick() {
      if(savedCallback.current){
        savedCallback.current();
      }
    }
    if(delay < 0){
      return () => {};
    }
    let id = setInterval(tick, delay);
    return () => clearInterval(id);
  }, [delay]);
}

function CarouselItem(props:CarouselItemProps){
  return <div className={props.className} style={props.style}>{props.children}</div>
}


function Carousel(props:CarouselProps){
    
    const  {state, ref } = useSize();
    const count = React.Children.count(props.children);
    const [wrapperStyle, setWrapperStyle] = useState({});
   
    let single_width = 0;
    if(state){
      single_width = state.width;
    }

    const [pos, setPos] = useState(0); 

    useEffect(() => {
      setWrapperStyle({
        "width":single_width*count,
        "height":"100%",
        "transform":"translate3d(-"+single_width*pos+"px,0px,0px)"
      });
    }, [pos,state.width]);

    
    let delay = -1;
    if(props.autoplay){
      delay = 3000;
    }
    useInterval(() => {
      if(pos === 2){
        setPos(0);
      }else{
        setPos(pos+1);
      }
    }, delay);
    

    const basicStyle = {
      "width":single_width,
      "height":"100%"
    }

    let carousel_list:JSX.Element[] = [];
    let dot_list:JSX.Element[] = [];
    
    React.Children.map(props.children, (element: React.ReactNode,idx) => {
      carousel_list.push(
        <div className="CarouselItem" style={basicStyle}  key={idx}>{element}</div>
      );
      dot_list.push(
        <li key={idx} className={idx===pos?"current":""}>
          <div className={"progressBar"}></div>
        </li>
      );
    });
	  return  (<div className={"Carousel"} ref={ref}>
              <div className={"CarouselWrapper"} style={wrapperStyle}>{carousel_list}</div>
              <ul className={"dot_list"} >{dot_list}</ul>
            </div>);
}

Carousel.CarouselItem = CarouselItem;
export default Carousel;
