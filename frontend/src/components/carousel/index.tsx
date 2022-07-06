import React, {useState, useEffect} from 'react';
import './index.css';
import iphone from  '../../assets/iphone.png';
import airpods from  '../../assets/airpods.png';
import tablet from  '../../assets/tablet.png';

export interface Carousel {
    (swipers: Array<string | number>): any;
}

function Carousel() {
    let currentPointIndex: any = 0;
    let [timer, setTimer] = useState(0);

     const add = (tag : Boolean, num?: any ) => {
         if (tag) {
            currentPointIndex++;
         } else {
            currentPointIndex = num;
         }
     };

    useEffect(() => {
        let img_box: any = document.querySelector('.img_box');
        let imgs = document.querySelectorAll('img');
        let sel_box: any = document.querySelector('.sel_box');
        let lis: any = sel_box.querySelectorAll('li');
        // let left_btn: any = document.querySelector('.left_btn');
        // let right_btn:any = document.querySelector('.right_btn');
        let sliLenth: number = lis.length;
        // 设置图片容器大小
        // imgContainerW：img_box本身宽度，为400
        let imgContainerW = img_box.offsetWidth;
        img_box.style.width = imgContainerW * sliLenth + 'px';
        // 设置容器位置
        img_box.style.left = 0 + 'px';
    
        // 设置第一个小图片作为当前按钮
        lis[0].className = 'cur';
        function swapImg() {
            // 修改img_box的定位，往左偏移 index * imgContainerW
            img_box.style.left = - currentPointIndex * imgContainerW + 'px';
            // 排他算法
            for (let i = 0; i < lis.length; i++) {
                lis[i].className = '';
            }
            console.log(currentPointIndex, sliLenth);
            // 修改小图标的样式
            lis[currentPointIndex].className = 'cur';
        }
      
        function swapFormat() {
            add(true);
            if (currentPointIndex >= sliLenth) {
                add(false, sliLenth);
                console.log(currentPointIndex, 777,sliLenth); // 进入下一张图片
                img_box.style.transition = 'all, linear, 1s';
                img_box.style.left = -currentPointIndex * imgContainerW + 'px';
                for (let i = 0; i < lis.length; i++) {
                    lis[i].className = '';
                }
                lis[0].className = 'cur';
                setTimeout(function () {
                    add(false, 0);
                    img_box.style.transition = ''; // 无过度
                    swapImg();
                }, 0);
            } else {
                img_box.style.transition = 'all, linear, 1.5s';
                swapImg();
            }
        }
        // 当鼠标在图片上、左箭头、右箭头时清除定时器，即图片不轮播
        img_box.addEventListener('mouseover', function () {
            clearInterval(timer);
        });
        let timers: any;
       timers = setInterval(swapFormat, 3000);
       setTimer(timers);
    }, [currentPointIndex]);
    return (
        <div className="banner_container">
            <div className='banner_content'>
                <ul className="img_box">
                    <li><img src={iphone} alt='1' />
                        <section className='s-content'>
                            <h3>xPhone</h3>
                            <p>Lots  to love. Less to spend.</p>
                            <p>Starting at $399.</p>
                        </section>
                    </li>
                    <li>
                        <img src={tablet} />
                        <section className='s-content'>
                            <h3>Tablet</h3>
                            <p>Just the right amount of everything.</p>
                         </section>
                    </li>
                    <li><img src={airpods} />
                        <section className='s-content'>
                            <h3>Buy a Tablet or xPhone for college.</h3>
                            <h3>Get arPods.</h3>
                        </section>
                    </li>
                </ul>
            </div>
            <ul className="sel_box">
                <li data-index="0"></li>
                <li data-index="1"></li>
                <li data-index="2"></li>
            </ul>
        </div>

    );
}
export default Carousel;
// ReactDOM.render(<LunBoComponent/>, document.getElementById('root'));
