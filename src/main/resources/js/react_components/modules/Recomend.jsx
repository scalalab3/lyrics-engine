/** @jsx React.DOM */

var React   = require('react');
var RecomendBox = require('../RecomendBox.jsx');

ReactDOM.render(
    <RecomendBox url="/api/recomend"/>,
    document.getElementById('content'));
