import imageIphone from '../../../assets/iphone.png';

interface Props {


}


const PageXPhone: React.FC<Props> = (props) => {
    return (
        <div className={'page-wrapper  xphone fx-column fx-v-center '}
            style={{ backgroundImage: `url(${imageIphone})` }}>
            <div className={'title mt200'}>xPhone</div>
            <div className={'desc'}>Lots to love.Less to spend.</div>
            <div className={'desc'}>Starting at $399.</div>
        </div>
    );
};

export default PageXPhone;
