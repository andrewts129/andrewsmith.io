import React from "react"
import {Card} from "Content"
import ReactDOM from "react-dom"

export class NavBar extends React.Component {
    render() {
        return (
            <nav id="main-navbar">
                <Button text="Projects"></Button>
                <Button text="Github"></Button>
                <Button text="Contact"></Button>
            </nav>
        );
    }
}

class Button extends React.Component {
    handleClick(buttonText) {
        switch (buttonText) {
            case "Projects":
                ReactDOM.render(<Card/>, document.getElementById("main-content"));
                break;
            default:
                //Do nothing
                break;
        }
    }

    getLink(buttonText) {
        switch (buttonText) {
            case "Projects":
                return "projects";
                break;
            case "Github":
                return "https://github.com/andrewts129";
                break;
            case "Contact":
                return "mailto:smith.11216@osu.edu";
                break;
            default:
                break;
        }
    }

    render() {
        return (
            <a className="main-navbar-button"
                    href={this.getLink(this.props.text)}
                    onClick={this.handleClick.bind(this, this.props.text)}>

                {this.props.text}
            </a>
        );
    }
}