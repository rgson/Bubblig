package se.rgson.da401a.bubblig.model;

import java.util.List;

import se.rgson.da401a.bubblig.model.bubbla.BubblaArticle;

public interface ModelCategoryListener {

	void onCategoryLoaded(List<BubblaArticle> articles);

}
