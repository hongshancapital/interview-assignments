import './index.css';

interface PropsParams {
    className: string;
    title: string;
    secondryTitle?: string;
}

const Content:React.FC<PropsParams> = (props) => {
    return <div className={props.className}>
      <div className='bigtitle'>{props.title}</div>
      <div className='secondrytitle'>{props.secondryTitle}</div>
    </div>
  }

  export default Content