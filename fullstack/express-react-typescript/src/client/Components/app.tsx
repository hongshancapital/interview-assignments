import * as React from 'react';
import '../Less/app.less';
import { apiRoute } from '../utils';
import { AppProps, AppStates } from "../../server/domain/IApp";
import { ILink } from "../../server/domain/ILink";
import { Post } from "../Services";

export default class App extends React.Component<AppProps, AppStates> {
    state: AppStates = {
        longLink: '',
        shortLink: '',
        longLinkText: '',
        shortLinkText: ''
    };

    getLongLink = async (): Promise<void> => {
        const {
            shortLink
        } = this.state;

        if (shortLink.trim()) {
            try {
                const res: ILink = await Post(
                    apiRoute.getRoute('link'),
                    { shortLink }
                );
                this.setState({
                    longLinkText: res.longLink,
                    response: res,
                });
            } catch (e) {
                this.setState({ shortLink: '' });
            }
        }
    };

    getShortLink = async (): Promise<void> => {
        const {
            longLink
        } = this.state;

        if (longLink.trim()) {
            try {
                const res: ILink = await Post(
                    apiRoute.getRoute('link'),
                    { longLink: longLink }
                );
                this.setState({
                    shortLinkText: res.shortLink,
                    response: res,
                });
            } catch (e) {
                this.setState({ longLink: '', shortLinkText: '' });
            }
        }
    }

    render() {
        const { longLinkText, shortLinkText } = this.state;
        const inputText = "请输入...";
        return (
            <div>
                <div className='line'>
                    <div>
                        <input onChange={e => this.setState({ longLink: e.target.value })} placeholder={inputText} />
                        <button onClick={this.getShortLink}>{"获取短链接"}</button>
                    </div>
                    <div className='line'>
                        <label>{"短链接: "}</label>
                        <h3>{shortLinkText}</h3>
                    </div>
                </div>
                <div className='line'>
                    <div>
                        <input onChange={e => this.setState({ shortLink: e.target.value })} placeholder={inputText} />
                        <button onClick={this.getLongLink}>{"获取长链接"}</button>
                    </div>
                    <div className='line'>
                        <label>{"长链接: "}</label>
                        <h3>{longLinkText}</h3>
                    </div>
                </div>
            </div>
        );
    }
}
