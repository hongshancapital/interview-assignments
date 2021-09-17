import react, { useState, useEffect, useRef, FC, ReactElement } from "react";
import './assets/index.css'

interface Iprops {
    active: boolean;
}

const Progress: FC<Iprops> = (props): ReactElement => {
    const ref = useRef(0);
    const [percentage, setPercentage] = useState(0);
    useEffect(() => {
       // const timer = setInterval(()=>setPercentage(pre => (pre >= 74 ? 0 : pre + 1)), 43);
        //return () => clearInterval(timer);
    }, [])

    useEffect(() => {
        ref.current = percentage;
    }, [percentage])

    useEffect(() => {
        setPercentage(()=>0);
        const timer = setInterval(()=>setPercentage(pre => (pre >= 74 ? 0 : pre + 1)), 39);
        return () => clearInterval(timer);
    }, [props.active])

    return (
        <div className='progress-box'>
            <div className='progress-group'>
                {
                    props.active ? <div className='progress-bg' style={{ width: percentage}}></div> : <div className='progress-bg'></div>
                }

            </div>
        </div>
    )
}

export default Progress