var AnalysisRow = React.createClass({
    render: function() {
        return(
                <tr key={this.props.id}>
                    <td>{this.props.id}</td>
                    <td>{this.props.created}</td>
                    <td>{this.props.algVersion}</td>
                </tr>
        );
    }
});

var AnalysisTable = React.createClass({
    getInitialState: function() {
        return {analyses: []};
    },
    
    fetchAnalyses: function() {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: true,  //TODO do we want caching here?
            success: function(data) {
                this.setState({analyses: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    
    componentDidMount: function() {
        this.fetchAnalyses();
        setInterval(this.fetchAnalyses, this.props.pollInterval);
    },
    
    render: function() {
        var analList = this.state.analyses.map(function(analysis) {
            return(<AnalysisRow key={analysis.id} id={analysis.id} created={analysis.created} algVersion={analysis.algorithmVersion} />);
        });
        
        return(
                <div className="analysisTable">
                    <table>
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Created</th>
                                <th>Algorithm version</th>
                            </tr>
                        </thead>
                        <tbody>
                            {analList}
                        </tbody>
                    </table>
                </div>
        );
    }
});

ReactDOM.render(
        <AnalysisTable url="http://localhost:8080/scias/rest/analysis" pollInterval={120000} />, //TODO switch to http://scias-vjuranek.rhcloud.com for OS deployment
        document.getElementById('data')
);