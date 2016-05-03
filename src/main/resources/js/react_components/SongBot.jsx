var React = require('react');

var SongBot =  React.createClass({
    render: function() {
        return (
            <div id="content_column_three" class="SongBot">
                <div id="ad_section">
                    <h1>Sponsors</h1>
                    <div class="ad_125x125_box"> <a href="#"><img src="./css/images/1.jpeg" alt="" /></a> </div>
                    <div class="ad_125x125_box"> <a href="#"><img src="./css/images/2.png" alt="" /></a> </div>
                    <div class="ad_125x125_box"> <a href="#"><img src="./css/images/3.jpeg" alt="" /></a> </div>
                    <div class="cleaner"> </div>
                </div>
                <div class="column_three_section">
                    <h1>Place for bot of another team</h1>
                    <ul class="popular_post">
                        Hello!! What do you like ?
                    </ul>
                </div>
                <div class="cleaner_with_divider"> </div>
            </div>
        );
    }
});
module.exports = SongBot