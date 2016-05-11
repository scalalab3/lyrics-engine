/** @jsx React.DOM */
var React = require('react');
var SongFooter = require('./SongFooter.jsx');
var SongMenu = require('./SongMenu.jsx');
var SongSearchColumn = require('./SongSearchColumn.jsx');
var SongList = require('./SongList.jsx');
var SongBot = require('./SongBot.jsx');


var SongBox = React.createClass({
    getInitialState: function() {
        return {data: []};
    },
    componentDidMount: function() {
        $.ajax({
            url: this.props.url,
            //dataType: 'json',
            crossDomain: true,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    performMagic: function() {
        alert('TAADAH!');
    },
    render: function() {
        return (
            <div className="commentBox">
                <SongMenu/>
                <div id="content_song">
                    <SongSearchColumn/>
                    <div id="content_column_two">
                        <SongList data={this.state.data} />
                    </div>
                    <SongBot/>
                    <div class="cleaner">&nbsp;</div>
                </div>
                <SongFooter/>
            </div>
        );
    }
});

module.exports = SongBox