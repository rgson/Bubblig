package se.rgson.da401a.bubblig.gui;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.model.Category;

public class GuiUtility {

	private GuiUtility() {
	}

	public static int findColorFor(Context context, Category category) {
		Resources res = context.getResources();
		switch (category) {
			case NYHETER:
				return res.getColor(R.color.category_nyheter);
			case VÄRLDEN:
				return res.getColor(R.color.category_världen);
			case SVERIGE:
				return res.getColor(R.color.category_sverige);
			case BLANDAT:
				return res.getColor(R.color.category_blandat);
			case MEDIA:
				return res.getColor(R.color.category_media);
			case POLITIK:
				return res.getColor(R.color.category_politik);
			case OPINION:
				return res.getColor(R.color.category_opinion);
			case EUROPA:
				return res.getColor(R.color.category_europa);
			case USA:
				return res.getColor(R.color.category_usa);
			case ASIEN:
				return res.getColor(R.color.category_asien);
			case EKONOMI:
				return res.getColor(R.color.category_ekonomi);
			case TEKNIK:
				return res.getColor(R.color.category_teknik);
			case VETENSKAP:
				return res.getColor(R.color.category_vetenskap);
			default:
				return res.getColor(android.R.color.transparent);
		}
	}

    public static int findLighterColorForRow(Category category, ViewGroup parent) {

        Resources res = parent.getResources();
        switch (category) {
            case NYHETER:
                return res.getColor(R.color.row_category_nyheter);
            case VÄRLDEN:
                return res.getColor(R.color.row_category_världen);
            case SVERIGE:
                return res.getColor(R.color.row_category_sverige);
            case BLANDAT:
                return res.getColor(R.color.row_category_blandat);
            case MEDIA:
                return res.getColor(R.color.row_category_media);
            case POLITIK:
                return res.getColor(R.color.row_category_politik);
            case OPINION:
                return res.getColor(R.color.row_category_opinion);
            case EUROPA:
                return res.getColor(R.color.row_category_europa);
            case USA:
                return res.getColor(R.color.row_category_usa);
            case ASIEN:
                return res.getColor(R.color.row_category_asien);
            case EKONOMI:
                return res.getColor(R.color.row_category_ekonomi);
            case TEKNIK:
                return res.getColor(R.color.row_category_teknik);
            case VETENSKAP:
                return res.getColor(R.color.row_category_vetenskap);
            default:
                return res.getColor(android.R.color.transparent);
        }
    }

}
