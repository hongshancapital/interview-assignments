
import Carousel from '../carousel';
import '../carousel/index.scss'

function Demo() {
  return  <Carousel interval={3000}>
      {
        [
          { title: 'learn react', imgURL: 'https://youimg1.c-ctrip.com/target/100i12000000rth5vAE6E.jpg' },
          { title: 'title2', imgURL: 'https://pic3.zhimg.com/v2-2ca5b73dd2c5b58850b9a74a025da13a_r.jpg' },
          { title: 'title3', imgURL: 'https://pic3.zhimg.com/v2-71eaff66299d9d788f2285e62ba0d84e_r.jpg' },
        ].map(el => <div key={el.title} style={{ height: 400, backgroundImage: `url(${el.imgURL})`, backgroundSize: 'cover' }}>
          {el.title}
        </div>)
      }
    </Carousel>
}
export default Demo;
