/** @jsx React.DOM */

var React   = require('react');
var SongBox = require('../SongBox.jsx');

ReactDOM.render(
    <SongBox url="http://localhost:9000/api/search"/>,
    document.getElementById('content'));
