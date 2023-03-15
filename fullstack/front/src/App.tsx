import { useState } from 'react';
import './App.css';
import { isUrl } from './utils/regex';

// 函数式组件
function App() {
  const [reault, setReault] = useState('');   // 返回链接
  const [search, setSearch] = useState('');   // 请求链接
  const [load, setLoad] = useState(false);
  const [isShort, setIsShort] = useState(false);

  const fetchData = async () => {
    try {
      setLoad(true);
      // search 正则符合连接
      if (!isUrl(search)) {
        alert('链接格式错误!!!');
        return;
      }
      
      const res = await fetch(`http://127.0.0.1:3001/`, {
        method: 'post',
        body: JSON.stringify({ search }),
        headers: { 'Content-Type': 'application/json' }
      });
      const json = await res.json();
      if (json && json.code === 0) {
        // 获取数据成功
        setReault(json?.data?.url);
        setIsShort(json?.data?.isShort)
      } else {
        // 获取数据失败
        setReault("没有查到相关数据");
      }
    } catch (error: any) {
      console.log(error)
    } finally {
      setLoad(false);
    }
  }

  return (
    <div className="app ">
      <p className="fw-bold fs-2 text-center">Engineer Assignment</p>
      <p className="text-center fs-6 fst-italic">(when you enter short url it response long url and vice versa)</p>
      <div className="input-group mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="input your link"
          aria-label="Recipient's username"
          aria-describedby="button-addon2"
          value={search}
          onChange={e => setSearch(e.target.value)} />
        <button
          className="btn btn-outline-secondary"
          type="button"
          id="button-addon2"
          onClick={fetchData}>
          GO CHECK
        </button>
      </div>

      <div className="list-group">
        {
          reault === "" ?
            <a href='' className="list-group-item list-group-item-action disabled">{load ? "Loading..." : "No Data"}</a> :
            <a href='' className="list-group-item list-group-item-action outputClass">{reault}</a>
        }
      </div>

      <div>
        {
          reault !== "" ? (
            isShort ?
              <p className="fw-lighter text-success fs-6">you enter short url and response long url.</p> :
              <p className="fw-lighter text-success fs-6">you enter long url and response short url.</p>
          ) : <p></p>
        }
      </div>
    </div>
  );
}

export default App;
