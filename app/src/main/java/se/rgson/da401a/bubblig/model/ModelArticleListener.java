package se.rgson.da401a.bubblig.model;

import se.rgson.da401a.bubblig.model.readability.ReadabilityResponse;

public interface ModelArticleListener {

	void onArticleLoaded(ReadabilityResponse article);

}
