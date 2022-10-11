import React, { useState, useEffect } from 'react'
import Carousel from './components/Carousel'
import './Carousel-Page.css'
import { getCarouselResourceList,getCarouselResourceList1 } from './api/api'

export default () => {

    let [resourceList, setResourceList] = useState<any>([])
    let [resourceList1, setResourceList1] = useState<any>([])


    useEffect(() => {
        getCarouselResourceList().then((res:any) => {

            setResourceList(res)
        })
        getCarouselResourceList1().then((res:any) => {

            setResourceList1(res)
        })
    }, []);



    return (


        

!resourceList.length||!resourceList1.length ? <></> : <div>

        
        <Carousel timeout={1000} direction="left" resourceList={resourceList}
            >
                {
                    new Array(resourceList.length).fill(void 0).map(s => Math.random())
                        .map((s, i) => (<span className="slid" key={s}>{resourceList[i].innerHTML}</span>))
                }
            </Carousel>

            <hr/>
        
        <Carousel timeout={3000} direction="right" resourceList={resourceList1}
            >
                {
                    new Array(resourceList1.length).fill(void 0).map(s => Math.random())
                        .map((s, i) => (<span className="slid" key={s}>{resourceList1[i].innerHTML}</span>))
                }
            </Carousel>
        

            
            
        </div>

       

        
    )
}