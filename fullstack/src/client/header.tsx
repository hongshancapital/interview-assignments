import * as React from 'react';
import { useState, useEffect } from 'react';

/* HOOK REACT EXAMPLE */
const Header = (props: headerProps) => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light fixed-top">
            <div className="container"><a className="navbar-brand" href="/"><i className="fa fa-cube"></i>Qingaoti ShortLink Demo</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                    <span className="navbar-toggler-icon"></span></button>
                <div className="collapse navbar-collapse">
                    <div className="dropdown-divider"></div>
                    <ul className="nav navbar-nav mr-auto">
                        <li className="nav-item"><a className="nav-link" href="/秦高梯-英文简历-20220507.pdf">Contact</a></li>
                    </ul>
                    <ul className="nav navbar-nav">
                        <li className="nav-item"><a className="nav-link" onClick={()=>{alert('to Example!')}}>Login</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

interface headerProps {}

/* CLASS REACT EXAMPLE */
// class App extends React.Component<IAppProps, IAppState> {
// 	constructor(props: IAppProps) {
// 		super(props);
// 		this.state = {
// 			name: null
// 		};
// 	}

// 	async componentDidMount() {
// 		try {
// 			let r = await fetch('/api/hello');
// 			let name = await r.json();
// 			this.setState({ name });
// 		} catch (error) {
// 			console.log(error);
// 		}
// 	}

// 	render() {
// 		return (
// 			<main className="container my-5">
// 				<h1 className="text-primary text-center">Hello {this.state.name}!</h1>
// 			</main>
// 		);
// 	}
// }

// export interface IAppProps {}

// export interface IAppState {
// 	name: string;
// }

export default Header;
