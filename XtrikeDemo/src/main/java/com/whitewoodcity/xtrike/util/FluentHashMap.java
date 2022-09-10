package com.whitewoodcity.xtrike.util;

import java.util.HashMap;
import java.util.Map;

public class FluentHashMap<K,V> extends HashMap<K,V> {
  public FluentHashMap() {
  }

  public FluentHashMap(Map<? extends K, ? extends V> m) {
    super(m);
  }

  public FluentHashMap<K,V> add(K key, V value) {
    super.put(key, value);
    return this;
  }
}
