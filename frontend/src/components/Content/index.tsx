import './index.css';

interface PropsParams {
    className: string;
    bigtitle1: string;
    bigtitle2?: string;
    secondryTitle1?: string;
    secondryTitle2?: string;
}

const Content:React.FC<PropsParams> = (props) => {
    return <div className={props.className}>
      <div className='bigtitle'>{props.bigtitle1}</div>
      <div className='bigtitle'>{props.bigtitle2}</div>
      <div className='secondrytitle'>{props.secondryTitle1}</div>
      <div className='secondrytitle'>{props.secondryTitle2}</div>
    </div>
  }

  export default Content