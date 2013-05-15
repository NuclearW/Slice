package com.nuclearw.slice;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;

public class HashBasedMatrix<R, C, L, V> {
	private final HashMap<R, HashMap<C, HashMap<L, V>>> storage;

	private HashBasedMatrix(HashMap<R, HashMap<C, HashMap<L, V>>> storage) {
		this.storage = storage;
	}

	public static <R, C, L, V> HashBasedMatrix<R, C, L, V> create() {
		return new HashBasedMatrix<R, C, L, V>(new HashMap<R, HashMap<C, HashMap<L, V>>>());
	}

	public boolean contains(Object rowKey, Object columnKey, Object layerKey) {
		if(rowKey == null || columnKey == null || layerKey == null) {
			return false;
		}

		HashMap<C, HashMap<L, V>> columnMap = storage.get(rowKey);
		if(columnMap == null) {
			return false;
		}

		HashMap<L, V> layerMap = columnMap.get(columnKey);
		if(layerMap == null) {
			return false;
		}

		return layerMap.containsKey(layerKey);
	}

	public V put(R rowKey, C columnKey, L layerKey, V value) {
		checkNotNull(rowKey);
		checkNotNull(columnKey);
		checkNotNull(layerKey);
		checkNotNull(value);

		HashMap<C, HashMap<L, V>> columnMap = storage.get(rowKey);
		if(columnMap == null) {
			columnMap = new HashMap<C, HashMap<L, V>>();
			storage.put(rowKey, columnMap);
		}

		HashMap<L, V> layerMap = columnMap.get(columnKey);
		if(layerMap == null) {
			layerMap = new HashMap<L, V>();
			columnMap.put(columnKey, layerMap);
		}

		return layerMap.put(layerKey, value);
	}

	public V get(R rowKey, C columnKey, L layerKey) {
		checkNotNull(rowKey);
		checkNotNull(columnKey);
		checkNotNull(layerKey);

		HashMap<C, HashMap<L, V>> columnMap = storage.get(rowKey);
		if(columnMap == null) {
			return null;
		}

		HashMap<L, V> layerMap = columnMap.get(columnKey);
		if(layerMap == null) {
			return null;
		}

		return layerMap.get(layerKey);
	}
}
