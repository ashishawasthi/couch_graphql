package dt.graphql.domain;

public class StirtConfig {
    private Integer id;
    private String ndfJumpsAndTurns;
    private String ndfCurveWeights;
    private String ndfOverride;

    public StirtConfig() {
    }

    public StirtConfig(final Integer id,
                       final String ndfJumpsAndTurns,
                       final String ndfCurveWeights,
                       final String ndfOverride) {
        this.id = id;
        this.ndfJumpsAndTurns = ndfJumpsAndTurns;
        this.ndfCurveWeights = ndfCurveWeights;
        this.ndfOverride = ndfOverride;
    }

    public Integer getId() {
        return id;
    }

    public String getNdfJumpsAndTurns() {
        return ndfJumpsAndTurns;
    }

    public String getNdfCurveWeights() {
        return ndfCurveWeights;
    }

    public String getNdfOverride() {
        return ndfOverride;
    }
}
