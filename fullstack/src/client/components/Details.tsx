import * as React from "react";
import { apiRoute } from "../apis/apiRoute";
import { DetailStates, DetailProps } from "./interface";
import { IUrl } from "../../server/interfaces/IUrl";
import { shortenUrl, getRawUrl } from "../apis";

const STORAGE_KEY = "URL_SHORTEN_TEST_LIST";
export default class List extends React.Component<DetailProps, DetailStates> {
  state: DetailStates = {
    inputRawUrl: "",
    inputUrlCode: "",
    rawUrl: "",
    shortenUrl: "",
    shortenMsg: "",
    queryMsg: "",
    rawUrls: [] as Array<IUrl>,
  };

  componentDidMount() {
    this.getHistoryList();
  }

  getHistoryList = () => {
    try {
      let records = localStorage.getItem(STORAGE_KEY);
      console.log("records=", records);
      if (records) {
        records = JSON.parse(records);
        this.setState({
          rawUrls: records as unknown as Array<IUrl>,
        });
      }
    } catch (err) {
      console.warn("got error when getting history list", err);
    }
  };

  updateHistoryList(newRecors: any) {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(newRecors));
    } catch (err) {
      console.warn("got error when updating history list", err);
    }
  }

  urlQuery = async (): Promise<void> => {
    try {
      const res: any = await getRawUrl(
        apiRoute.getApi(`${this.state.inputUrlCode}`)
      );

      if (res.message) {
        this.setState({ queryMsg: res.message });
      } else {
        this.setState({ rawUrl: res.data.rawUrl, queryMsg: "" });
      }
    } catch (e) {
      this.setState({ queryMsg: e.message });
    }
  };

  urlShorten = async (): Promise<void> => {
    const { inputRawUrl } = this.state;

    if (inputRawUrl.trim()) {
      try {
        const res: any = await shortenUrl(apiRoute.getApi("shorten"), {
          rawUrl: this.state.inputRawUrl,
        });
        const arr = this.state.rawUrls.concat([res.data]);

        if (res.message) {
          this.setState({ shortenMsg: res.message });
        } else {
          this.setState(
            {
              shortenUrl: res.data.shortenUrl,
              rawUrls: arr,
              shortenMsg: "",
            },
            () => {
              this.updateHistoryList(arr);
            }
          );
        }
      } catch (e) {
        this.setState({ shortenMsg: e.message });
      }
    }
  };

  render() {
    const { rawUrl, shortenUrl, rawUrls, shortenMsg, queryMsg } = this.state;

    const shortenHolder = "please type rawUrl to shorten";
    const queryHolder = "please type urlCode to query";

    return (
      <div className="detail-wrapper">
        <div className="test-title">Url shorten test</div>
        <div className="test-row">
          <input
            className="test-box"
            onChange={(e) => this.setState({ inputRawUrl: e.target.value })}
            placeholder={shortenHolder}
          />
          <button className="test-btn" onClick={this.urlShorten}>
            Shorten Url
          </button>
        </div>
        {shortenUrl && (
          <div className="test-row test-result">
            <label>Shortened Url: </label>
            <h3>{shortenUrl}</h3>
          </div>
        )}
        {shortenMsg && (
          <div className="test-row test-hint">
            <label>hint message: </label>
            <h3>{shortenMsg}</h3>
          </div>
        )}
        <div className="test-row row2">
          <input
            className="test-box"
            onChange={(e) => this.setState({ inputUrlCode: e.target.value })}
            placeholder={queryHolder}
          />
          <button className="test-btn" onClick={this.urlQuery}>
            Query RawUrl
          </button>
        </div>
        {rawUrl && (
          <div className="test-row test-result">
            <label>rawUrl for query: </label>
            <h3>{rawUrl}</h3>
          </div>
        )}
        {queryMsg && (
          <div className="test-row test-hint">
            <label>hint message: </label>
            <h3>{queryMsg}</h3>
          </div>
        )}
        {rawUrls.length > 0 && (
          <div className="history-list">
            <h3>Shorten history records：</h3>
            <ul>
              {rawUrls.map((item) => {
                return (
                  <li key={item._id} className="his-row">
                    <span>
                      rawUrl:<span className="row-item">{item.rawUrl}</span>
                    </span>
                    <span className="back-sec">
                      urlCode:<span className="row-item">{item.urlCode}</span>
                    </span>
                  </li>
                );
              })}
            </ul>
          </div>
        )}
      </div>
    );
  }
}
