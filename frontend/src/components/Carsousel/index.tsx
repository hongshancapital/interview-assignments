import React, { useState, useEffect ,useRef , useMemo } from "react";
import  './carsousel.css';


const Carsoule = (parmas:any)=>{
    const [stepIndex, setStepIndex]: [number , Function] = useState(0); // stepIndex
    const {imgList=[] , stepsTimes = 3000} = parmas;
    const updateInterval :any = useRef(null);  // interval
    const stepIndexRef :any = useRef(0);  //  全局ref

    useEffect(()=>{
        updateInterval.current = setInterval(()=>{
            updateStepIndex();
        } , stepsTimes);
        return ()=>{
            if(updateInterval.current){
                clearInterval(updateInterval.current)
            }
        }
    }, [])

    const updateStepIndex =() =>{
        // 运行先加+1
        const _index = stepIndexRef.current +1;
        stepIndexRef.current =  _index>=parmas.imgList.length? 0 : stepIndexRef.current +1;
        setStepIndex(stepIndexRef.current);
    }

    const onClickSteps = (_index:number)=>{
        document.getAnimations().forEach((anim) => {
            anim.cancel();
            anim.play();
        });
        stepIndexRef.current = _index;
        setStepIndex(_index);
        // 清空
        if(updateInterval.current){
            clearInterval(updateInterval.current)
        }
        updateInterval.current = setInterval(()=>{
            updateStepIndex();
        } , stepsTimes);
    }

    const  renderContent = () =>{
        return <>
          <div className="carsousel_inner" style={{ transform: `translateX(-${stepIndex * 100}%)` }}>
                <div style={{ width: "100%", height: "100vh"}}>
                    {
                        imgList?.map((item:any)=>{
                           return  <img key={item.key} src={item.url} height={'100%'} width={'100%'}/>
                        })
                    }
                </div>
            </div>
            <div className='steps'>
                {
                    imgList?.map((item:any , index:number)=>{
                        return <div className='indicator_outer' key={item.key} onClick={() => onClickSteps(index)}>
                            <div className='indicator_inside' 
                            style={{
                                animationDuration:  index === stepIndex ? `${stepsTimes}ms` : "0s",
                                backgroundColor: index === stepIndex ? '#DDDDDD' : '',
                                }}/>
                           </div>
                    })
                }
            </div>
        </>
    }

    const carsouselContent = useMemo(()=>renderContent() , [imgList , stepsTimes ,  stepIndex])
    

    return (
        <div className='carsousel'>
            {carsouselContent}
        </div>
    )
};

export default Carsoule;