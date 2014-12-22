package se.rgson.da401a.bubblig.model;

import java.util.List;

import se.rgson.da401a.bubblig.model.bubbla.BubblaArticle;

public interface CategoryListener {

	void onCategoryLoaded(List<Article> articles);

}
