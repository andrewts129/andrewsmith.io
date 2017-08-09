import React from "react"
import ReactDOM from "react-dom"

export class Content extends React.Component {
    createCard(cardType) {
        ReactDOM.render(Card, this);
    }

    render() {
        return (
            <div id="main-content"></div>
        );
    }
}

export class Card extends React.Component {
    render() {
        return (
            <div className="card">fuck</div>
        );
    }
}