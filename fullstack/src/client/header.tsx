import * as React from 'react';

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

export default Header;
