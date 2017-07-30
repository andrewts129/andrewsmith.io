import React from "react"

export class Content extends React.Component {
    createCard(cardType) {
        alert("Creating card: " + cardType);
    }

    render() {
        return (
            <div id="main-content"></div>
        );
    }
}

class Card extends React.Component {
    render() {
        return (
            <div className="card">fuck</div>
        );
    }
}