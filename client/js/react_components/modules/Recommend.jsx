/** @jsx React.DOM */

var React   = require('react');
var RecommendBox = require('../RecommendBox.jsx');

ReactDOM.render(
    <RecommendBox url="http://localhost:9000/api/recommend"/>,
    document.getElementById('content'));
