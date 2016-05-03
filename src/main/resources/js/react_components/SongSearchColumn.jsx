/** @jsx React.DOM */
var React = require('react');

var SongSearchColumn = React.createClass({
    render: function() {
        return (
            <div id="content_column_one">
                <div class="column_one_section">
                    <h1>Song genres</h1>
                    <ul>
                        <li><a href="#">Alternative Music </a></li>
                        <li><a href="#">Blues</a></li>
                        <li><a href="#">Classical Music</a></li>
                        <li><a href="#">Country Music</a></li>
                        <li><a href="#">Dance Music</a></li>
                        <li><a href="#">Easy Listening</a></li>
                        <li><a href="#">Electronic Music</a></li>
                        <li><a href="#">Jazz</a></li>
                    </ul>
                </div>
                <div class="cleaner_with_divider"> </div>
                <div class="column_one_section">
                    <h1>Music artists</h1>
                    <ul>
                        <li><a href="#">The Beatles</a></li>
                        <li><a href="#">Garth Brooks</a></li>
                        <li><a href="#">Elvis Presley</a></li>
                        <li><a href="#">Led Zeppelin</a></li>
                        <li><a href="#">Eagles</a></li>
                        <li><a href="#">Billy Joel</a></li>
                        <li><a href="#">Michael Jackson</a></li>
                        <li><a href="#">Pink Floyd</a></li>
                        <li><a href="#">Elton John</a></li>
                        <li><a href="#">AC/DC</a></li>
                    </ul>
                </div>
                <div class="cleaner_with_divider"> </div>
                <div class="column_one_section">
                    <h1>Album</h1>
                    <ul>
                        <li><a href="#">Thriller</a></li>
                        <li><a href="#">Back in Black</a></li>
                        <li><a href="#">The Dark Side of the Moon</a></li>
                        <li><a href="#">The Bodyguard</a></li>
                        <li><a href="#">Bat Out of Hell</a></li>
                        <li><a href="#">Saturday Night Fever</a></li>
                    </ul>
                </div>
            </div>
        );
    }
});
module.exports = SongSearchColumn