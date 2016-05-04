/** @jsx React.DOM */

var React   = require('react');
var SongBox = require('../SongBox.jsx');

ReactDOM.render(
    <SongBox url="/api/search"/>,
    document.getElementById('content'));
