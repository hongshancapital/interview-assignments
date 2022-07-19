import React, {useState, useEffect} from "react";
import './index.css'



const Carousel = (props:any) =>{
  const { children } = props
  const [childrenNode,setChildrenNode] = useState< any > (null)
  const childrenLength =  children.props.children.length;


  useEffect(() => {
    const childs: any = document.querySelector('.children');
    setChildrenNode(childs);
    [].forEach.call(childs?.children,(item:any) => {
      item.setAttribute('style', `width: ${100 / childrenLength}%;height:100% `)
    });
    childs && childs.setAttribute('style', `width:${childrenLength* 100}%;height:100%`)
  },[])

  const useInterval = (callbck:any, num:any) => {
    useEffect(() => {
      const start = new Date().getTime()
      const time = setInterval(() => {
        callbck(new Date().getTime() - start)
      }, num)
      return ()=>clearInterval(time)
    },[])
  }

  const useSlider = (num:number,speed:number = 3000) => {
    const [slider, setSlider] = useState(0)
    useInterval((diff:any)=> {
      setSlider(()=>Math.floor(diff/speed) % num)
    }, 300)
    return slider
  }



  const slider = useSlider(childrenLength)

  useEffect(() => {
    const lines = document.querySelector('.line')?.children || [];
    lines[0]?.setAttribute('class','animate')
    if (childrenNode) {
      [].forEach.call(lines,(item:any) => {
        item.setAttribute('class', '')
      });
      childrenNode.style.transform = `translateX(-${100 * slider / childrenLength}%)`;
      lines[slider]?.setAttribute('class','animate')
    }
  }, [slider])

    return (
      <div className="container">
        {children}
        <div>
          <ul className="line">
            {
              children.props.children.map((item:any) => {
                return (
                  <li key={item}></li>
                )
              })
            }
          </ul>
        </div>
      </div>
    )

}

export default Carousel;
