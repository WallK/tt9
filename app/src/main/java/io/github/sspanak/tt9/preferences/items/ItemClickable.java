package io.github.sspanak.tt9.preferences.items;

import androidx.preference.Preference;

import java.util.ArrayList;
import java.util.List;

import io.github.sspanak.tt9.Logger;
import io.github.sspanak.tt9.preferences.SettingsStore;

abstract public class ItemClickable {
	private long lastClickTime = 0;

	protected final Preference item;
	private final ArrayList<ItemClickable> otherItems = new ArrayList<>();


	public ItemClickable(Preference item) {
		this.item = item;
	}


	public void disable() {
		item.setEnabled(false);
	}


	public void enable() {
		item.setEnabled(true);
	}


	public void enableClickHandler() {
		item.setOnPreferenceClickListener(this::debounceClick);
	}


	public ItemClickable setOtherItems(List<ItemClickable> others) {
		otherItems.clear();
		otherItems.addAll(others);
		return this;
	}


	protected void disableOtherItems() {
		for (ItemClickable i : otherItems) {
			i.disable();
		}

	}


	protected void enableOtherItems() {
		for (ItemClickable i : otherItems) {
			i.enable();
		}

	}

	protected boolean debounceClick(Preference p) {
		long now = System.currentTimeMillis();
		if (now - lastClickTime < SettingsStore.PREFERENCES_CLICK_DEBOUNCE_TIME) {
			Logger.d("debounceClick", "Preference click debounced.");
			return true;
		}
		lastClickTime = now;

		return onClick(p);
	}


	abstract protected boolean onClick(Preference p);
}
