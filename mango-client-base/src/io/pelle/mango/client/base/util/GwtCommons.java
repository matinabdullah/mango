package io.pelle.mango.client.base.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtCommons implements EntryPoint {

	private static GwtCommons instance;

	public static GwtCommons getInstance() {
		if (instance == null) {
			throw new RuntimeException("GwtCommons module not loaded, add GwtCommons to your module definitions xml");
		}

		return instance;
	}

	public static String DEFAULT_CHARACTER_WIDTH_SEED = "abcdefghijklmnopqrstuvwxyz";

	private Map<String, Float> averageWidths = new HashMap<String, Float>();

	/**
	 * Returns the average character width using the default seed
	 * {@link GwtCommons#DEFAULT_CHARACTER_WIDTH_SEED}
	 *
	 * @return average character width
	 */
	public float getAverageCharacterWidth() {
		return getAverageCharacterWidth(DEFAULT_CHARACTER_WIDTH_SEED, null);
	}

	/**
	 * Returns the average character width using the default seed
	 * {@link GwtCommons#DEFAULT_CHARACTER_WIDTH_SEED}
	 *
	 * @param uppercase
	 *            true to compute the width for uppercase characters
	 * @return average character width
	 */
	public float getAverageCharacterWidth(boolean uppercase) {
		String seed = DEFAULT_CHARACTER_WIDTH_SEED;

		if (uppercase) {
			seed = DEFAULT_CHARACTER_WIDTH_SEED.toUpperCase();
		}

		return getAverageCharacterWidth(seed, null);
	}

	private static String getWidthMapKey(String seed, String cssStyle) {
		return String.valueOf(seed) + String.valueOf(cssStyle);
	}

	/**
	 * Returns the average character width for a text
	 *
	 * @param seed
	 *            text to use for measurement
	 * @param cssStyle
	 *            optional css style for the text
	 * @return average character width
	 */
	public float getAverageCharacterWidth(String seed, String cssStyle) {
		String key = getWidthMapKey(seed, cssStyle);

		if (!this.averageWidths.containsKey(key)) {
			Element widthCalculationDiv = DOM.createDiv();

			String additionalCssStyle = (cssStyle == null) ? "" : cssStyle;

			widthCalculationDiv.setAttribute("style", "position: absolute; float: left; white-space: nowrap; visibility: hidden;" + additionalCssStyle);

			widthCalculationDiv.setInnerText(seed);
			RootPanel.getBodyElement().appendChild(widthCalculationDiv);

			int clientWidth = widthCalculationDiv.getClientWidth();

			widthCalculationDiv.removeFromParent();

			float averageWidth = (float) clientWidth / seed.length();
			this.averageWidths.put(key, averageWidth);
		}

		return this.averageWidths.get(key);
	}

	@Override
	public void onModuleLoad() {
		instance = this;

	}
}