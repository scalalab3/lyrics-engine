/** @jsx React.DOM */

var React   = require('react');
var RecommendBox = require('../RecommendBox.jsx');

ReactDOM.render(
    <RecommendBox url="/api/recommend"/>,
    document.getElementById('content'));
