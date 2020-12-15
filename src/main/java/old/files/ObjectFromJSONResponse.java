import java.util.ArrayList;

public class ObjectFromJSONResponse {

    private String kind;

    @Override
    public String toString() {
        return "ObjectFromJSONResponse{" +
                "kind='" + kind + '\'' +
                ", UrlObject=" + UrlObject +
                ", QueriesObject=" + QueriesObject +
                ", ContextObject=" + ContextObject +
                ", SearchInformationObject=" + SearchInformationObject +
                ", items=" + items +
                '}';
    }

    Url UrlObject;
    Queries QueriesObject;
    Context ContextObject;
    SearchInformation SearchInformationObject;
    ArrayList<Object> items = new ArrayList<Object>();


    // Getter Methods

    public String getKind() {
        return kind;
    }

    public Url getUrl() {
        return UrlObject;
    }

    public Queries getQueries() {
        return QueriesObject;
    }

    public Context getContext() {
        return ContextObject;
    }

    public SearchInformation getSearchInformation() {
        return SearchInformationObject;
    }

    // Setter Methods

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setUrl(Url urlObject) {
        this.UrlObject = urlObject;
    }

    public void setQueries(Queries queriesObject) {
        this.QueriesObject = queriesObject;
    }

    public void setContext(Context contextObject) {
        this.ContextObject = contextObject;
    }

    public void setSearchInformation(SearchInformation searchInformationObject) {
        this.SearchInformationObject = searchInformationObject;
    }
}

class SearchInformation {
    private float searchTime;
    private String formattedSearchTime;
    private String totalResults;
    private String formattedTotalResults;


    // Getter Methods

    public float getSearchTime() {
        return searchTime;
    }

    public String getFormattedSearchTime() {
        return formattedSearchTime;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getFormattedTotalResults() {
        return formattedTotalResults;
    }

    // Setter Methods

    public void setSearchTime(float searchTime) {
        this.searchTime = searchTime;
    }

    public void setFormattedSearchTime(String formattedSearchTime) {
        this.formattedSearchTime = formattedSearchTime;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public void setFormattedTotalResults(String formattedTotalResults) {
        this.formattedTotalResults = formattedTotalResults;
    }
}

class Context {
    private String title;


    // Getter Methods

    public String getTitle() {
        return title;
    }

    // Setter Methods

    public void setTitle(String title) {
        this.title = title;
    }
}

class Queries {
    ArrayList<Object> request = new ArrayList<Object>();


    // Getter Methods


    // Setter Methods


}

class Url {
    private String type;
    private String template;


    // Getter Methods

    public String getType() {
        return type;
    }

    public String getTemplate() {
        return template;
    }

    // Setter Methods

    public void setType(String type) {
        this.type = type;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}