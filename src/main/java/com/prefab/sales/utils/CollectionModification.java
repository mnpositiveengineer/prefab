package com.prefab.sales.utils;

import com.prefab.sales.cost.interfaces.CreateCostGroups;
import com.prefab.sales.utils.exceptions.ObjectAlreadyAddedException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;

import java.util.*;
import java.util.function.Consumer;

public class CollectionModification {

    public static <T> void addToCollection(Collection<T> collection, T object) throws ObjectAlreadyAddedException
    {
        if (collection.isEmpty() || !(collection.contains(object))) {
            collection.add(object);
        } else {
            throw new ObjectAlreadyAddedException();
        }
    }

    public static <T> void removeFromCollection(Collection<T> collection, T object) throws ObjectNotInCollectionException
    {
        if (collection.isEmpty() || !(collection.contains(object)))
            throw new ObjectNotInCollectionException();
        collection.remove(object);
    }

    public static <K,V> void addToMap(Map<K, Set<V>> map, K key, V value)
    {
        if (map.isEmpty() || !(map.containsKey(key))) {
            Set<V> list = new HashSet<>();
            list.add(value);
            map.put(key, list);
        }
        else {
          map.get(key).add(value);
        }
    }

    public static <K,V> void removeKeyFromMap(Map<K, Set<V>> map, K key)
            throws ObjectNotInCollectionException
    {
        if (map.isEmpty() || !(map.containsKey(key)))
            throw new ObjectNotInCollectionException();
        map.remove(key);
    }

    public static <K,V> void removeValueFromMap(Map<K, Set<V>> map, K key, V value)
        throws ObjectNotInCollectionException
    {
        if (map.isEmpty() || !(map.containsKey(key)) || !(map.get(key).contains(value)))
            throw new ObjectNotInCollectionException();
        map.get(key).remove(value);
        removeKeyWIthNoValues(map);
    }

    private static <K,V> void removeKeyWIthNoValues(Map<K, Set<V>> map)
    {
       Set<K> keysToRemove = new HashSet<>();;
        for (Map.Entry<K, Set<V>> entrySet : map.entrySet()) {
            if (entrySet.getValue().isEmpty())
                keysToRemove.add(entrySet.getKey());
        }
        keysToRemove.forEach(object -> map.remove(object));
    }

    public static <K extends CreateCostGroups,V> void addAllElementsToGroup(Map<K, Set<V>> map,
                                                                            K group, Set<V> elementsToAdd,
                                                                            Set<V> elementsInGroup)
    {
        if(!(map.isEmpty()))
            clearAllElementsFromAllGroups(map);
        Consumer<V> addToMap = element -> addToMap(map, group, element);
        Consumer<V> addToGroup = element -> elementsInGroup.add(element);
        elementsToAdd.forEach(addToMap.andThen(addToGroup));
    }

    private static <K extends CreateCostGroups,V> void clearAllElementsFromAllGroups(Map<K, Set<V>> map)
    {
        if (!(map.isEmpty())) {
            for (K group: map.keySet()) {
                removeAllElementsFromGroup(map, group);
            }
            map.clear();
        }
    }

    public static <K extends CreateCostGroups,V> void removeAllElementsFromGroup(Map<K, Set<V>> map, K group)
    {
        group.getElementsInGroup().clear();
        if(map.containsKey(group))
            map.remove(group);
    }

    public static <K,V> void addSelectedElementsToGroup(Map<K, Set<V>> map, K group, V element,
                                                        Set<V> elementsInGroup) throws ObjectAlreadyAddedException
    {
        checkIfElementIsInMap(map, element);
        CollectionModification.addToMap(map, group, element);
        elementsInGroup.add(element);
    }

    public static <K,V> void removeSelectedElementsFromGroup(Map<K, Set<V>> map, K group, V element,
                                                             Set<V> elementsInGroup)
            throws ObjectNotInCollectionException
    {
        removeValueFromMap(map, group, element);
        elementsInGroup.remove(element);
    }

    private static <K,V> void checkIfElementIsInMap(Map<K, Set<V>> map, V element) throws ObjectAlreadyAddedException
    {
        if (map.isEmpty())
            return;
        for (Set<V> value : map.values()) {
            if (value.contains(element))
                throw new ObjectAlreadyAddedException();
        }
    }
}