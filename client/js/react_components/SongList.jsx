/** @jsx React.DOM */
var React = require('react');
var Song = require('./Song.jsx');
var SongList = React.createClass({
    render: function() {
        var songNodes = this.props.data.map(function(song) {
            return (
                <Song name={song.songName} author = {song.artistName} album = {song.albumName} text = {song.text} key={song.trackId}>
                </Song>
            );
        });
        return (
            <div className="songList">
                {songNodes}
            </div>
        );
    }
});

module.exports = SongList