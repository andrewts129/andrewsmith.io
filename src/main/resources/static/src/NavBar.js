import React from "react"

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
                alert("WIP");
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
                return "mailto:andrewts129@gmail.com";
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