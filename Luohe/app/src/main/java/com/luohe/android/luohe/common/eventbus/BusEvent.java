package com.luohe.android.luohe.common.eventbus;

/**
 * Created by GanQuan on 16/4/23.
 */
public class BusEvent<T> {
	EventType mEventType;
	T mSubType;

	/**
	 * 响应type
	 */

	public BusEvent(EventType eventType, T subcribeType) {
		this.mEventType = eventType;
		this.mSubType = subcribeType;
	}

	public BusEvent(EventType eventType) {
		this.mEventType = eventType;
	}

	public EventType getEventType() {
		return this.mEventType;
	}

	public T getSubType() {
		return this.mSubType;
	}

	public enum EventType {
		WRITE_THEME_SUC,
		CHOICE_TIME,
		LOG_OUT, SELECT_REPORT_TYPE
	}

}
