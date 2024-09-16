import React, { useEffect, useState } from "react";
import "./Carousel.css";

/**
 * @description 广告数据
 */
const AD_DATA = [
    {
        title: "xPhone",
        description: "Lots to love. Less to spend. Starting at $399.",
        src: "https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00011-226.jpg",
        color: "white",
        backgroundColor: "rgb(20, 20, 20)"
    },
    {
        title: "Tablet",
        description: "Just the right amount of everything.",
        src: "https://img1.baidu.com/it/u=3781078293,1337723101&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313",
        color: "black",
        backgroundColor: "white"
    },
    {
        title: "Get arPods.",
        description: "Lots to love. Less to spend Starting at $399",
        src: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fauctions.c.yimg.jp%2Fimages.auctions.yahoo.co.jp%2Fimage%2Fdr000%2Fauc0102%2Fusers%2F2203a602a1c4dd4715b67d87efe2e8b88cff573f%2Fi-img640x480-1581065640mh3sr3194272.jpg%3Fx-oss-process%3Dimage%2Fresize%2Cw_100&refer=http%3A%2F%2Fauctions.c.yimg.jp&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657269498&t=3d6702f91a53b6cb485061dadddd8392",
        color: "black",
        backgroundColor: "white"
    }
];


/**
 * @description 单页组件
 */
function Item(props: any){
    return (
        <div className="page" style={{...props}}>
            <div className="column">
                <div className="title">{ props.title }</div>
                <div className="description">{ props.description }</div>
                <div className="logo"><img src={ props.src } width="200" height="200"></img></div>
            </div>
        </div>
    )
}



/**
 * @description 页码条
 */
function Bar(props: any){

    let timer:any = false, 
        [pageIndex, setPageIndex] = useState(1),
        [rates, setRates] = useState(0);

    let togglePages = () => {     
        pageIndex = pageIndex + 1;
        if( pageIndex > AD_DATA.length ) pageIndex = 1;
        setPageIndex( pageIndex );
        props.setPageIndex( pageIndex );
    }

    useEffect(()=>{
        timer && clearTimeout( timer );
        timer = setTimeout(()=>{
            if( rates >= 100 ){
                setRates(0); 
                togglePages();
            }else{
                setRates( ++rates );
            }
        }, 50)

        return ()=>{ clearTimeout(timer);}
    });

    let listItems = AD_DATA.map((item, index)=>{
        return index == pageIndex-1 
            ? (<div className="out" key={ index }>
                <div className="in" style={{ width:rates+'%' }}></div>
              </div>)
            : (<div className="out" key={ index }>
                <div className="in" style={{ width:0 }}></div>
              </div>)
    })
    

    return (
        <div className="pagination">
            {listItems}
        </div>
    );
}



/**
 * @description 幻灯片组件
 */
function Carousel(){

    let [currentLeft, setLeft] = useState('0vw');

    let setPageIndex = ( pageIndex:number = 1 ) => 
            setLeft( (pageIndex-1)*-100 + "vw" );
    

    let listItems = AD_DATA.map(
        (item: any, index: number) => <Item { ...item } key={ index }></Item>
    );

    return (
        <>
            <div className="container">
                <div id="marquee" className="marquee" style={{left: currentLeft}}>
                    { listItems }
                </div>
            </div>
            <Bar setPageIndex={ setPageIndex }></Bar>
        </>
    );
}

export default Carousel;