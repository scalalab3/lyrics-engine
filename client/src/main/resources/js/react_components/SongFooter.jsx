/** @jsx React.DOM */

var React = require('react');

var SongFooter = React.createClass({
    render: function() {
        return (
            <div id="bottom_panel" className="SongFooter">
                <div class="bottom_panel_section"> <a href="#">Home</a> | <a href="#">Gallery</a> | <a href="#">About Us</a> | <a href="#">Contact Us</a><br />
                    <br />
                    Copyright &copy; 2016 <a href="#"><strong>Adform</strong></a> | Designed by <a target="_blank" rel="nofollow" href="http://www.templatemo.com">lyrics team</a></div>
                <div class="cleaner"> </div>
            </div>
        );
    }
});

module.exports = SongFooter;