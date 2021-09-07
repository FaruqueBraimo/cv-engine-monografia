package Engine.Application.Form.cv.engine.search;

import Engine.Application.Form.cv.engine.model.Candidate;
import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import com.algolia.search.models.settings.IndexSettings;

import java.util.Arrays;
import java.util.Collections;

public class ArgoliaSerach {

    SearchIndex<Candidate> index;

    public ArgoliaSerach() {
        SearchClient client = DefaultSearchClient.create("VBD35KGQQ7", "e0b04c9f532b74482421e1ead914035c");
        index = client.initIndex("candidate", Candidate.class);

    }

    public void saveCandidate(Candidate candidate) {
        index.saveObject(candidate);
        configure();

    }

    public void configure() {
        index.setSettings(new IndexSettings().setCustomRanking(Collections.singletonList("desc(name)")));

        index.setSettings(new IndexSettings().setSearchableAttributes(
                Arrays.asList("skills", "experience","province", "name", "birth_date")));

    }

    public SearchResult<Candidate> search(String name) {
        return  index.search(new Query(name));
    }


}
