import React from 'react';
import ReactDOM from 'react-dom';
import './style.css';
import registerServiceWorker from './registerServiceWorker';
import {NavBar} from "./NavBar";
import {Animation} from "./Animation";
import {Content} from "./Content";

class App extends React.Component {
    render() {
        return (
            <div>
                <Animation/>
                <Content/>
                <NavBar/>
            </div>
        );
    }
}

ReactDOM.render(<App/>, document.getElementById("root"));
registerServiceWorker();
