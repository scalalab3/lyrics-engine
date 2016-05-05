/** @jsx React.DOM */
var React = require('react');
var RecommendForm = require('./RecommendForm.jsx')
var SongFooter = require('./SongFooter.jsx');
var SongMenu = require('./SongMenu.jsx');
var SongSearchColumn = require('./SongSearchColumn.jsx');
var SongBot = require('./SongBot.jsx');
var SongList = require('./SongList.jsx');

var RecommendBox = React.createClass({
    getInitialState: function() {
        return {data: []};
    },
    handleCommentSubmit: function(song) {
        var songs = this.state.data;
        $.ajax({
            url: this.props.url,
            dataType: 'json',

                headers: {
                    //'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            cache: false,
            crossDomain: true,
            type : "POST",
            data: JSON.stringify(song),
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                this.setState({data: song});
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
        //$.ajax({
        //  //  url: this.props.url,
        //    url: "http://localhost:9000/api/search",
        //    headers: {
        //        'Accept': 'application/json',
        //        'Content-Type': 'application/json'
        //    },
        //    dataType: 'json',
        //    cache: false,
        //    crossDomain: true,
        //    //type : "GET",
        //    //data: JSON.stringify(song),
        //    success: function(data) {
        //        this.setState({data: data});
        //    }.bind(this),
        //    error: function(xhr, status, err) {
        //        this.setState({data: song});
        //        console.error(this.props.url, status, err.toString());
        //    }.bind(this)
        //});
    },
    render: function() {
        return (
            <div className="commentBox">
                <SongMenu/>
                <div id="content_song">
                    <SongSearchColumn/>
                    <div id="content_column_two">
                        <div class="form-style-3">
                            <RecommendForm onCommentSubmit={this.handleCommentSubmit}/>
                        </div>
                        <SongList data={this.state.data} />
                    </div>
                    <SongBot/>
                    <div class="cleaner">&nbsp;</div>
                </div>
            </div>
        );
    }
});
module.exports = RecommendBox