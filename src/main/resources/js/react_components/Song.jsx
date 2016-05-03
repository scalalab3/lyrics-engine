/** @jsx React.DOM */
var React = require('react');

var Song = React.createClass({
    render: function() {
        return (
            <div class="column_two_section">
                <h1>{this.props.name}</h1>
                <p><strong>Author: </strong>{this.props.author}</p>
                <p><strong>Album: </strong>{this.props.album}</p>
                <p><strong>Text: </strong>{this.props.text}</p>
            </div>
        );
    }
});

module.exports = Song