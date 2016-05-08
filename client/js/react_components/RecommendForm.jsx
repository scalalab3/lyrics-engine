var RecommendForm = React.createClass({
    getInitialState: function() {
        return {trackId: '', artistName: '', songName: '', albumName: '', text: ''};
    },
    handleAuthorChange: function(e) {
        this.setState({artistName: e.target.value});
    },
    handleTextChange: function(e) {
        this.setState({text: e.target.value});
    },
    handleSongNameChange: function(e) {
        this.setState({songName: e.target.value});
    },
    handleAlbumNameChange: function(e) {
        this.setState({albumName: e.target.value});
    },
    handleSubmit: function(e) {
        e.preventDefault();
        var artistName = this.state.artistName.trim();
        var text = this.state.text.trim();
        var songName = this.state.songName.trim();
        var albumName = this.state.albumName.trim();
        if (!text) {
            return;
        }
        this.props.onCommentSubmit({artistName: artistName, songName: songName, albumName:albumName, text: text});
        this.setState({trackId: '', artistName: '', songName: '', albumName: '', text: ''});
    },
    render: function() {
        return (
            <div class ="recommendForm">
                    <form className="form-style-3" onSubmit={this.handleSubmit}>
                        <fieldset><legend>Recommend songs</legend>
                            <label for="songAuthor"><span>Song author </span>
                            <input
                                type="text"
                                name = "songAuthor"
                                placeholder="Song author"
                                value={this.state.author}
                                onChange={this.handleAuthorChange}
                            /></label>

                            <label for="songName"><span>Song name</span>
                            <input
                                type="text"
                                name ="songName"
                                placeholder="Song name"
                                value={this.state.songName}
                                onChange={this.handleSongNameChange}
                            /></label>
                            <label for="albumName"><span>Album name</span>
                            <input
                                type="text"
                                name="albumName"
                                placeholder="Album name"
                                value={this.state.albumName}
                                onChange={this.handleAlbumNameChange}
                            /></label>
                            <label for="songText"><span>Song text <span class="required">*</span></span>
                            <input
                                type="text"
                                name="songText"
                                placeholder="Song text"
                                value={this.state.text}
                                onChange={this.handleTextChange}
                            /></label>
                            <input type="submit" value="Recommend" />
                        </fieldset>
                    </form>
             </div>
        );
    }
});
module.exports = RecommendForm